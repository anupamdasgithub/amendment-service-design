# Generated sources (reference copy)

These Java files are **not written by hand**. They are produced by the
`kogito-maven-plugin` during `mvn package`, which reads the BPMN and DMN
models under `src/main/resources/` and generates the runtime and REST layer
from them. They are normally build output under `target/generated-sources/`
and are excluded from the build; this copy is checked in only so the
generated API surface can be read on GitHub without building the project.

Do not edit these files. They are regenerated on every build. To change the
API, change the source model (the `.bpmn` or `.dmn`) and rebuild.

## How generation works

For each process definition (`.bpmn`), the plugin emits:

- `*Process.java` and `*ProcessInstance.java` — the executable process.
- `*Resource.java` — a Spring `@RestController` that exposes the process and
  its human tasks as REST endpoints. There are no hand-written controllers;
  this is the entire API. The task path segments are derived from the BPMN
  node **name** (spaces become underscores), which is why, for example, the
  node "Request proof of address" is reached at
  `/amendment_coa/{id}/Request_proof_of_address/{taskId}`.
- `*Model*.java`, `*TaskInput.java`, `*TaskOutput.java`, `*TaskModel.java` —
  the typed input/output models for the process and each task.

For each decision (`.dmn`), the plugin emits a `*Resource.java` decision
endpoint plus its input/output types, and contributes to
`org/kie/kogito/app/DecisionModels.java`.

## API resources in this copy

Process REST resources:

- `org/drools/bpmn2/Amendment_requestResource.java` — parent case container
- `org/drools/bpmn2/Amendment_coaResource.java` — change of address
- `org/drools/bpmn2/Amendment_conResource.java` — change of name
- `org/drools/bpmn2/Amendment_joint_to_soleResource.java` — joint to sole

Decision REST resources:

- `.../AmendmentAdmissibilityResource.java`
- `.../AmendmentSequencingResource.java`
- `.../JointToSoleEligibilityResource.java`

## Regenerating

```bash
mvn -Pcommunity -P'!bamoe' -DskipTests generate-sources
```

The output appears under `target/generated-sources/kogito/`.
