# Customer Account Amendments Service

BAMOE (jBPM/Kogito) on Spring Boot, embedded engine, per-service persistence.

Automates customer-initiated account amendments: change of address (CoA),
change of name (CoN), and joint-to-sole conversion.

See `ADR-001` for the process decomposition decision and its rationale.

## Structure

```
src/main/resources/processes/
  amendment-request.bpmn          Parent case container + event subprocesses
  amendment-coa.bpmn              Child: change of address
  amendment-con.bpmn              Child: change of name
  amendment-joint-to-sole.bpmn    Child: conversion + consent event subprocess

src/main/resources/decisions/
  amendment-admissibility.dmn     Parent gate, per requested type
  amendment-sequencing.dmn        Parallel vs ordered execution plan
  joint-to-sole-eligibility.dmn   Child detailed eligibility

src/main/java/com/bank/amendments/
  model/                          Case file and domain classes
  handler/                        Custom WorkItemHandlers (core banking)
  service/                        Decision invocation services

src/main/resources/db/migration/
  V1__kogito_process_store.sql    Engine persistence + jobs service
  V2__amendment_case_store.sql    Application case file and audit
```

## Design decisions embodied here

**Parent as case container, children as call activities.** The parent owns
the case file, correlation id and overall SLA. CoA, CoN and joint-to-sole are
call activities resolving to separate process definitions, so each is
versioned and deployed independently. They are deliberately *not* event
subprocesses.

**Event subprocesses for ad-hoc concerns only.** Extra document, additional
verification, cancellation and SLA escalation attach to the parent and can
fire regardless of which child is running. Consent dispute nests inside the
joint-to-sole child because it is specific to that type.

**Decisions in DMN, integrations in handlers.** Business logic lives in
decision tables. Integrations go through WorkItemHandlers: the shipped REST
Service Call handler for standard REST, and a custom handler for the core
banking adapter where the shipped one cannot express the transport.

**Two-stage eligibility.** The parent evaluates cheap admissibility using
data available at intake. Type-specific eligibility runs inside each child
where the required data has been gathered. Putting all of it in the parent
would force the parent to know the internals of all three journeys.

**Every outcome carries a reason code.** Refusal reason codes are a
first-class DMN output, not an afterthought. They are what the customer sees
and what the regulator asks about.

## Building

The project has two Maven profiles.

### bamoe (default) — the design of record

Uses IBM BAMOE artifacts. **These are not on Maven Central.** IBM distributes
them through a BAMOE Maven repository I host myself, as a `.zip` or a
container image, obtained through IBM Passport Advantage or Fix Central. This
is a licensed IBM distribution and requires entitlement.

```bash
mvn -Dbamoe.repo.url=http://localhost:8080/ compile
```

Or configure the repository in `~/.m2/settings.xml` under the profile id
`ibm-bamoe-enterprise-maven-repository`. I set `bamoe.version` to the release I am licensed for — version strings carry an IBM build suffix such as
`9.4.2-ibm-0002`, so the bare `9.4.2` will not resolve.

### community — local verification

Builds against the upstream Kogito project on Maven Central: the same
underlying engine, no IBM entitlement needed. Use it to verify that the BPMN,
DMN and Java wiring compile.

```bash
mvn -Pcommunity -P'!bamoe' compile
mvn -Pcommunity -P'!bamoe' test
```

Confirm the current Kogito release and set `kogito.community.version`:

```bash
curl -s https://repo1.maven.org/maven2/org/kie/kogito/\
kogito-processes-spring-boot-starter/maven-metadata.xml | grep release
```

The artifact coordinates differ from BAMOE, so this profile is for local
verification only — it is not a deployment target, and the BAMOE profile
remains the design of record.

### CI

CI runs neither profile. A public GitHub Actions runner has no route to the
IBM repository, and building against community artifacts would verify
coordinates the programme does not use. CI validates the BPMN and DMN models
and applies the SQL migrations against a real Postgres, all of which need no
engine artifacts at all.

## Local development

The inner loop runs entirely on this machine. Testing a rule change requires
no commit and no pipeline.

```bash
docker compose up -d
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### Testing a single rule

```bash
mvn test -Dtest=AdmissibilityDecisionTest
```

Edit the decision table, rerun, see the result. Seconds, not a pipeline run.

Reserve commit and CI/CD for promotion to shared environments, where the
versioning and audit value of Git actually applies.

### Exercising the process

```bash
curl -X POST http://localhost:8080/amendment-request \
  -H 'Content-Type: application/json' \
  -d '{"request": {"customerId": "C1", "accountId": "A1", "requestedTypes": ["COA"]}}'
```

## Persistence model

Two stores, deliberately separate:

- **Engine store** (`process_instances`, `correlation_instances`,
  `job_details`) holds execution state, checkpointed at each wait state and
  read back to rehydrate. Owned by this service alone, not a shared BPMDB.
- **Application store** (`amendment_request`, `amendment_item`,
  `amendment_party_consent`, `amendment_document`, `amendment_audit`) holds
  the business case file and audit trail, which must outlive the process
  instance for regulatory retention.

`amendment_audit` is append-only. Do not grant UPDATE or DELETE in any
environment.

## CI

`scripts/ci-check.sh` runs the full gate locally; `.github/workflows/ci.yml`
runs the same checks on push and PR.

```bash
./scripts/ci-check.sh
```

Model validation runs *before* the build. A decision table with the wrong
number of rule entries is well-formed XML but wrong logic — entries shift
into neighbouring columns and the table evaluates against the wrong inputs.
Catching that with a two-second Python check beats waiting for code
generation to choke on it.

The two validators can also be run directly:

```bash
python3 scripts/validate_dmn_arity.py src/main/resources/decisions
python3 scripts/validate_bpmn_refs.py src/main/resources/processes
```

## Exercising the service

`docs/amendment-service.postman_collection.json` imports into Postman and
covers the whole API. I set the `baseUrl` collection variable to match how I
started the app: `http://localhost:8090` for the `dev` profile (see
`application-dev.yaml`), or `http://localhost:8080` otherwise.

The API is generated from the BPMN by the Kogito Maven plugin — there are no
hand-written controllers. Each process definition (`amendment_request`,
`amendment_coa`, `amendment_con`, `amendment_joint_to_sole`) is exposed as a
REST resource. Human-task nodes become task sub-resources whose path segment
is the node **name** with spaces replaced by underscores (for example the
node "Request proof of address" becomes `Request_proof_of_address`, and
"Underwriting review" becomes `Underwriting_review`) — not the
`drools:taskName`.

### Starting a request

All journeys start by posting a case file to the parent:

```bash
curl -X POST {{baseUrl}}/amendment_request \
  -H 'Content-Type: application/json' \
  -d '{"requestId":"REQ-COA","amendmentType":"COA","accountStatus":"ACTIVE",
       "requestorIsParty":true,"mandatePermits":true,"channel":"DIGITAL",
       "screeningOutcome":"CLEAR","inFlightAmendment":false,
       "accountIsJoint":false,"riskBand":"LOW","addressVerified":true}'
```

The seven **Routing scenarios** in the collection each post one such body.
The scenarios are the point: two requests differing in one field take
different paths through the same definitions.

| Scenario | Key field | Path taken |
|---|---|---|
| 1. Verified address | `addressVerified: true` | applies the change in core banking and completes |
| 2. Unverified address | `addressVerified: false` | requests proof of address, parks at a user task |
| 3. Change of name | `amendmentType: CON` | screening then maker-checker approval |
| 4. Name change with hit | `screeningOutcome` hit | routed to financial-crime review |
| 5. Joint to sole, consent | `jointLiabilities: false` | consent branch, parks at *Await consent response* |
| 6. Joint to sole, deceased | `otherPartyStatus: DECEASED` | diverted to the specialist bereavement journey |
| 7. Multiple amendments | several `items` | sequenced or parallel per the sequencing decision |

### Instance and task queries

Each child process supports listing its running instances and their human
tasks. The collection's **Instances** and **Human tasks** folders wrap these:

```
GET {{baseUrl}}/amendment_coa                                  # list CoA instances
GET {{baseUrl}}/amendment_coa/{id}/tasks?user=amendments-ops   # tasks on one instance
GET {{baseUrl}}/amendment_joint_to_sole                        # list JTS instances
GET {{baseUrl}}/amendment_joint_to_sole/{id}/tasks?user=underwriting
```

List requests carry a small test script that captures the first instance id
and task id into collection variables (`coaInstanceId`, `jtsInstanceId`,
`taskId`), so the follow-up completion requests resolve without copying ids
by hand. Always run the relevant **List instances** then **List tasks**
request immediately before completing a task, so both the instance id and the
task id come from the same live instance.

### Completing a human task

Task completion is a phase transition on the process-scoped task resource. The
path is `/{process}/{instanceId}/{Node_Name}/{taskId}` with a `phase` query
parameter and the acting `user` (and `group` where the node requires one):

```
POST {{baseUrl}}/amendment_coa/{{coaInstanceId}}/Request_proof_of_address/{{taskId}}?user=amendments-ops&phase=complete
POST {{baseUrl}}/amendment_joint_to_sole/{{jtsInstanceId}}/Underwriting_review/{{taskId}}?user=underwriting&phase=complete
```

Body is the task output model, or `{}` when the node collects nothing. The
`user` must match the node's potential owner or the transition is rejected.

### Walking a journey end to end

Change of address, held at evidence:

1. Run **2. Unverified address** — starts a CoA instance that parks at
   *Request proof of address*.
2. Run **List CoA instances**, then **List CoA tasks (amendments-ops)** —
   captures `coaInstanceId` and `taskId`.
3. Run **Complete the proof of address task** — the instance resumes,
   applies the address in core banking, and completes.

Joint to sole, underwriting branch:

1. Post scenario 5 with `jointLiabilities: true` — the eligibility decision
   returns `REVIEW_REQUIRED`, so the instance routes to *Underwriting review*
   instead of the consent branch.
2. Run **List joint-to-sole instances**, then **List joint-to-sole tasks
   (underwriting)** — captures `jtsInstanceId` and `taskId`.
3. Run **Complete underwriting review** — the instance resumes onto the
   consent branch and parks at *Await consent response*.

The same endpoints work from curl; the collection is the source of truth for
the exact shapes. The individual request bodies are also checked in under
`docs/requests/` (see `docs/requests/README.md`) for reuse from curl or any
other client.

## Scope and data

This is a design and reference implementation. All domain content —
eligibility rules, refusal reason codes, thresholds, product and party
structures — is **synthetic and illustrative**. It models the shape of a
retail banking amendment journey; it is not derived from, and does not
represent, any institution's actual policies, rules or data.

## Known open items

Carried from ADR-001 section 7:

- Confirm the business does not require adaptive case management. This design
  delivers a structured process with case-like framing, not ACM.
- Confirm the current BAMOE release and support lifecycle dates before
  locking `bamoe.version` in the POM.
- Confirm the state of case management support in the 9.x line.
- Decide rule change cadence: rules compile into the service at build time,
  so if the business needs rule changes without redeploying, high-churn
  decisions should move to a separately deployable decision service.

## Verification note

Artifact structure follows the documented BPMN 2.0 and DMN 1.4 schemas and
the Kogito/BAMOE conventions. Version-specific details — exact starter
artifact ids, persistence DDL column definitions, and the WorkItemHandler
interface package — should be validated against the documentation for the
BAMOE release selected before first build.
