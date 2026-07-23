# ADR-001 addendum — dependency and tooling findings

Date: 23 July 2026
Status: Informational. Does not change the decision in ADR-001.

## Resolves open item: BAMOE Maven artifacts

ADR-001 section 7 listed "confirm current BAMOE release and support lifecycle
dates" as open. Partially resolved.

The artifact coordinates are confirmed from IBM's Maven library reference. The
Spring Boot workflow dependency set is:

| Concern | Artifact |
|---|---|
| BOM | `com.ibm.bamoe:bamoe-bom` |
| Workflow engine | `org.jbpm:jbpm-with-drools-spring-boot-starter` |
| Build plugin | `org.kie.kogito:kogito-maven-plugin` |
| Persistence | `org.kie:kie-addons-springboot-persistence-jdbc` |
| Jobs Service | `org.kie:kogito-addons-springboot-embedded-jobs-jpa` |
| User Tasks | `org.jbpm:jbpm-addons-springboot-usertask-storage-jpa` |
| Data-Index | `org.kie:kogito-addons-springboot-data-index-jpa` |
| Data-Audit | `org.kie:kogito-addons-springboot-data-audit-jpa` |
| Process events | `org.kie:kie-addons-springboot-events-process` |
| Process management | `org.kie:kie-addons-springboot-process-management` |
| REST work item | `org.kie.kogito:kogito-rest-workitem` |
| Monitoring | `org.kie:kie-addons-springboot-monitoring-prometheus` |
| ILMT compliance | `com.ibm.bamoe:bamoe-ilmt-compliance-spring-boot-pamoe` |

Version strings carry an IBM build suffix, for example `9.4.2-ibm-0002`.

**Still open:** the exact supported release and its lifecycle dates for this
programme, which depend on the licence held.

## New constraint: artifacts are not on Maven Central

BAMOE artifacts are distributed through a BAMOE Maven repository hosted
locally, obtained from IBM Fix Central. They do not resolve from Maven Central.

Consequences for the programme:

- Build infrastructure must host or mirror the BAMOE repository. Plan for a
  Nexus or Artifactory mirror rather than a per-developer local repository.
- Any CI runner without a route to that mirror cannot compile the service.
  The repository's public CI therefore validates models and migrations only.
- Onboarding a developer requires repository setup, not just a clone.

## Partially addresses: ops portal gap

ADR-001 section 5.2 recorded that cross-instance visibility must be assembled
through the data index rather than read from a bundled portal.

BAMOE ships `com.ibm.bamoe:bamoe-spring-boot-web-console`. This reduces, but
does not remove, the gap: it is an operational console rather than a
customer-facing or business work-management portal. The build-your-own ops
queue over the Data Index remains the plan for business users.

## Requires verification: Spring Boot workflow support level

An IBM community response indicates that in the BAMOE 9 line, Spring Boot was
supported for decision use cases (DMOE) from 9.2, with workflow use cases
(PAMOE) scheduled for later support. The 9.4 product documentation does
document Spring Boot workflow services, which suggests this has since landed.

Because ADR-001 selected Spring Boot partly for team familiarity, confirm the
support status for workflow use cases against the licensed release before
build starts. If Spring Boot workflow support is not GA for that release,
Quarkus becomes the recommended runtime and ADR-001 section 2 should be
revisited.
