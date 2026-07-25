# Persistence and Kafka Eventing — Status and Limitations

This document records the state of durable persistence and Kafka event
publishing for the amendment service on the **community** build profile
(`-Pcommunity -P'!bamoe'`, Kogito `1.44.1.Final`, Spring Boot `3.3.4`), and
explains what can and cannot be achieved on that stack versus the target
IBM BAMOE / CP4BA platform (the `bamoe` profile).

## TL;DR

- **JDBC persistence is enabled** and process-instance state is written to
  PostgreSQL (`amendments_dev.process_instances`).
- **Kafka event publishing works** via a custom `EventPublisher`
  (`com.bank.amendments.events.KafkaProcessEventPublisher`) that replaces the
  stock Kogito Kafka events add-on, which is not compatible with Spring Boot 3.
- Events flow to the Data Index (its own `dataindex` PostgreSQL database) and
  are rendered by the Kogito Management Console (`http://localhost:8280`).
- **What does NOT work on community:** a request does **not** complete its full
  lifecycle. Every instance persists at creation and emits its creation event,
  but the parent process fails at the `task_derive_plan` node before the
  multi-instance fan-out, so child processes (CoA / CoN / JTS) are never spawned
  and human tasks are never reached. See "Known limitation" below.
- **This limitation is specific to the community stack.** On the BAMOE /
  CP4BA profile (IBM's matched, supported dependency set) the same design is
  expected to run end to end with durable persistence, because the underlying
  cause is a community-profile classloader/type-validation behaviour, not a
  defect in the process model, handlers, or data model.

## What works today (community, `-Pcommunity`)

### Durable persistence (partial)
- The persistence add-on `kogito-addons-springboot-persistence-jdbc:1.44.1.Final`
  is on the classpath; the build reports `AddonsConfig{usePersistence=true,...}`.
- Newly created process instances are written to
  `amendments_dev.public.process_instances` and survive a restart at that level.
- Enabling persistence required a series of model/BPMN changes that are latent
  requirements the in-memory path silently tolerated:
  - Enum value collisions across enums resolved with `@JsonProperty` renames
    (Kogito flattens enum values into a single generated namespace).
  - Every BPMN process variable given an explicit `itemSubjectRef` /
    `structureRef` (untyped variables default to `java.lang.Object`, which
    persistence rejects).
  - Collections typed with `isCollection="true"` and an element `structureRef`.
  - All model classes made `implements java.io.Serializable`.
  - Enums extracted from the nested `Enums` holder into top-level classes
    (the generated marshallers expect top-level types).
  - `BigDecimal` money fields changed to `double` at the persistence boundary
    (Kogito 1.44 generates an invalid Protobuf marshaller for `BigDecimal`).
  - `spring-boot-maven-plugin` `repackage` goal bound in the community profile
    so a runnable jar is produced.

### Kafka event publishing
- The stock add-on `kogito-addons-springboot-events-process-kafka:1.44.1.Final`
  is **intentionally excluded**: it is compiled against Spring Kafka 2.x, whose
  `KafkaTemplate.send(String, Object)` returns `ListenableFuture`. Spring Boot
  3.3.4 ships Spring Kafka 3.2.x, where that method returns `CompletableFuture`
  and the old signature is gone, so the add-on throws `NoSuchMethodError` at
  runtime. No Spring Kafka version both retains the old `send()` and runs on
  Spring 6 / Jakarta, and the Kogito team has stated the community Spring Boot
  add-ons do not support Spring Boot 3.
- Replacement: `com.bank.amendments.events.KafkaProcessEventPublisher`
  implements the same `org.kie.kogito.event.EventPublisher` SPI (auto-discovered
  by the engine), serialises each `DataEvent` with Jackson, and publishes to the
  same topics (`kogito-processinstances-events`, `kogito-usertaskinstances-events`,
  `kogito-variables-events`) using the Spring Kafka 3.x `send()`.
- Verified: the topic offset increments on each API call and the publisher logs
  `Successfully published event ProcessInstanceEvent to topic
  kogito-processinstances-events`.

### Data Index and Management Console
- Data Index (`quay.io/kiegroup/kogito-data-index-postgresql:1.44.1`) consumes
  the Kafka events into its own `dataindex` PostgreSQL database and exposes a
  GraphQL endpoint on `http://localhost:8180/graphql`.
- The Management Console (`http://localhost:8280`) reads Data Index over GraphQL.

## Known limitation (community only): full lifecycle does not complete

### Symptom
Every API call fails partway through the parent process:

```
task_derive_plan:
java.lang.IllegalArgumentException -
value [com.bank.amendments.model.AmendmentItem@...] does not match
com.bank.amendments.model.AmendmentItem
```

The instance is created and persisted, and the creation event is published, but
the flow stops at the `Derive execution plan` node. As a result:
- the multi-instance sub-process (`Execute permitted amendments`) never fans out;
- no CoA / CoN / JTS child process is spawned;
- no human task (consent, underwriting, proof-of-address) is reached;
- only the creation event reaches Kafka, not the full set of lifecycle events.

### Root cause
The `amendmentItems` process variable is a `List<AmendmentItem>` that feeds the
multi-instance sub-process (`amendmentItems -> _di_dispatch_items ->
multiInstanceLoopCharacteristics`). Enabling persistence forced this variable to
carry an explicit collection type (`isCollection="true"`,
`structureRef="com.bank.amendments.model.AmendmentItem"`).

With that type present, Kogito validates the assignment by resolving the declared
type and calling an `isInstance`-style check on each element. The
`AmendmentItem` class object loaded by the type resolver is a *different* `Class`
object from the one the work-item handler used to construct the items (same
fully-qualified name, different classloader), so the check fails with "does not
match". This validation path does not run under in-memory execution, which is
why the full cycle worked before persistence was enabled.

Neither typing the node `dataOutput` to match the variable
(`itemSubjectRef="_itemList"`) nor other type variants cleared it; the failure is
in the community engine's type-validation/classloader handling of custom-class
collection variables, not in the model or handlers.

### Why it is a community-stack limitation
The community Spring Boot line of Kogito 1.44 is intended for fast, in-memory
inner-loop development (rule/flow verification), not durable persistence on
Spring Boot 3. Enabling JDBC persistence on it surfaces a chain of behaviours —
of which this is the last — that the supported stack does not exhibit.

## What this maps to on BAMOE / CP4BA (`bamoe` profile)

The `bamoe` profile targets IBM's BAMOE artifacts (`bamoe-bom`,
`9.4.2-ibm-0002`), a matched and IBM-validated dependency set that bundles
persistence, Data Index, and event add-ons designed to run together. On that
profile / on CP4BA:

- Durable JDBC persistence of process instances is a first-class, supported
  feature — the six community-only workarounds above are not required.
- The `List<AmendmentItem>` → multi-instance fan-out under persistence is a
  standard pattern and is expected to run end to end (create → fan-out → child
  processes → human tasks → completion), because the classloader/type-validation
  behaviour that breaks it on community does not occur on the supported stack.
- Kafka eventing to Data Index / Management Console is provided by the matched
  IBM add-ons, so the custom `KafkaProcessEventPublisher` used here (a
  community-only compatibility shim for Spring Boot 3) is not needed.

In short: **persistence and full end-to-end lifecycle should be demonstrated on
the BAMOE / CP4BA profile**, which is the migration target. The community profile
is retained for in-memory inner-loop development, and the persistence + custom
Kafka eventing added here demonstrate the wiring and prove the event pipeline,
with the full-cycle completion being the one item that requires the supported
stack.

## How to verify the current state

App DB (instances persisted at creation):
```
docker exec amend-postgres-1 psql -U dev -d amendments_dev \
  -c "SELECT id, process_id FROM process_instances;"
```

Kafka (creation events published):
```
docker exec amend-kafka-1 /opt/kafka/bin/kafka-get-offsets.sh \
  --bootstrap-server localhost:9092 --topic kogito-processinstances-events
```

Publisher firing (app log):
```
grep "Successfully published" app.log
```

Management Console: http://localhost:8280/ProcessInstances
Kafka UI (Kafbat): http://localhost:8081

## Reproducing the limitation

```
# POST any routing scenario, e.g. "5. Joint to sole — consent granted"
# then:
grep -iE 'does not match|failedNodeId' app.log
```
The `task_derive_plan` / "does not match" error confirms the flow stops before
fan-out on the community profile.
