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

**BAMOE artifacts are not on Maven Central.** IBM distributes them through a
BAMOE Maven repository that you host locally, downloaded from IBM Fix Central.
Building this project requires that repository to be configured and reachable.

Point the build at it either by property:

```bash
mvn -Dbamoe.repo.url=http://localhost:8080/ compile
```

or by adding the repository to `~/.m2/settings.xml`, or by mirroring the
BAMOE artifacts into your organisation's Nexus/Artifactory.

Set `bamoe.version` in `pom.xml` to the release you are licensed for. Version
strings carry an IBM build suffix — for example `9.4.2-ibm-0002` — so the bare
`9.4.2` will not resolve.

This is also why CI does not run `mvn compile`: a public GitHub Actions runner
has no route to the IBM repository. CI validates the BPMN and DMN models and
applies the SQL migrations against a real Postgres, both of which need no IBM
artifacts. The Java build and the DMN unit tests run locally.

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
