package org.drools.bpmn2;

import org.drools.bpmn2.Amendment_requestModel;
import org.kie.api.definition.process.Process;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.jbpm.process.core.datatype.impl.type.ObjectDataType;
import org.drools.core.util.KieFunctions;
import org.jbpm.process.core.datatype.impl.type.StringDataType;
import org.jbpm.process.core.datatype.impl.type.BooleanDataType;
import org.jbpm.process.core.datatype.impl.type.UndefinedDataType;
import org.jbpm.process.core.datatype.impl.type.IntegerDataType;

@org.springframework.stereotype.Component("amendment_request")
public class Amendment_requestProcess extends org.kie.kogito.process.impl.AbstractProcess<org.drools.bpmn2.Amendment_requestModel> {

    @org.springframework.beans.factory.annotation.Autowired()
    @org.springframework.beans.factory.annotation.Qualifier("amendment_coa")
    org.kie.kogito.process.Process<Amendment_coaModel> processamendment_coa;

    @org.springframework.beans.factory.annotation.Autowired()
    @org.springframework.beans.factory.annotation.Qualifier("amendment_con")
    org.kie.kogito.process.Process<Amendment_conModel> processamendment_con;

    @org.springframework.beans.factory.annotation.Autowired()
    @org.springframework.beans.factory.annotation.Qualifier("amendment_joint_to_sole")
    org.kie.kogito.process.Process<Amendment_joint_to_soleModel> processamendment_joint_to_sole;

    @org.springframework.beans.factory.annotation.Autowired()
    public Amendment_requestProcess(org.kie.kogito.app.Application app, org.kie.kogito.correlation.CorrelationService correlations) {
        super(app, java.util.Arrays.asList(), correlations);
        activate();
    }

    public Amendment_requestProcess() {
    }

    @Override()
    public org.drools.bpmn2.Amendment_requestProcessInstance createInstance(org.drools.bpmn2.Amendment_requestModel value) {
        return new org.drools.bpmn2.Amendment_requestProcessInstance(this, value, this.createProcessRuntime());
    }

    public org.drools.bpmn2.Amendment_requestProcessInstance createInstance(java.lang.String businessKey, org.drools.bpmn2.Amendment_requestModel value) {
        return new org.drools.bpmn2.Amendment_requestProcessInstance(this, value, businessKey, this.createProcessRuntime());
    }

    public org.drools.bpmn2.Amendment_requestProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.correlation.CompositeCorrelation correlation, org.drools.bpmn2.Amendment_requestModel value) {
        return new org.drools.bpmn2.Amendment_requestProcessInstance(this, value, businessKey, this.createProcessRuntime(), correlation);
    }

    @Override()
    public org.drools.bpmn2.Amendment_requestModel createModel() {
        return new org.drools.bpmn2.Amendment_requestModel();
    }

    public org.drools.bpmn2.Amendment_requestProcessInstance createInstance(org.kie.kogito.Model value) {
        return this.createInstance((org.drools.bpmn2.Amendment_requestModel) value);
    }

    public org.drools.bpmn2.Amendment_requestProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.Model value) {
        return this.createInstance(businessKey, (org.drools.bpmn2.Amendment_requestModel) value);
    }

    public org.drools.bpmn2.Amendment_requestProcessInstance createInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.drools.bpmn2.Amendment_requestProcessInstance(this, this.createModel(), this.createProcessRuntime(), wpi);
    }

    public org.drools.bpmn2.Amendment_requestProcessInstance createReadOnlyInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.drools.bpmn2.Amendment_requestProcessInstance(this, this.createModel(), wpi);
    }

    protected org.kie.api.definition.process.Process process() {
        RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("amendment_request", true);
        factory.variable("request", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(com.bank.amendments.model.AmendmentRequest.class), null, "customTags", "input");
        factory.variable("accountId", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", null);
        factory.variable("requestId", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.variable("cancelled", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", null);
        factory.variable("amendmentType", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.variable("accountStatus", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.variable("requestorIsParty", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("mandatePermits", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("channel", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.variable("screeningOutcome", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.variable("inFlightAmendment", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("accountIsJoint", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("addressVerified", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("rescreenOutcome", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("allConsentsGranted", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("nameChangeReason", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("otherPartyStatus", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("jointLiabilities", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("remainingPartyEligible", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("riskBand", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.variable("admissibility", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", "output");
        factory.variable("amendmentItems", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("itemType", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", null);
        factory.variable("isCoa", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", null);
        factory.variable("isCon", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", null);
        factory.variable("isJointToSole", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", null);
        factory.variable("anyPermitted", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", null);
        factory.variable("permittedCount", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Integer.class), null, "customTags", "input");
        factory.variable("includesJointToSole", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("includesCon", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("includesCoa", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Boolean.class), null, "customTags", "input");
        factory.variable("ExecutionPlan", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", "output");
        factory.name("Amendment Request");
        factory.packageName("org.drools.bpmn2");
        factory.dynamic(false);
        factory.version("1.0");
        factory.type("BPMN");
        factory.visibility("Public");
        factory.metaData("TargetNamespace", "https://bank.com/bpmn/amendments");
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode1 = factory.startNode(1);
        startNode1.name("Request submitted");
        startNode1.interrupting(false);
        startNode1.metaData("UniqueId", "start");
        startNode1.metaData("x", 60);
        startNode1.metaData("width", 36);
        startNode1.metaData("y", 80);
        startNode1.metaData("height", 36);
        startNode1.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode2 = factory.workItemNode(2);
        workItemNode2.name("Load account and verify identity");
        workItemNode2.workName("AccountIntakeTask");
        workItemNode2.workParameter("NodeName", "Load account and verify identity");
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_requestId", "requestId", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("amendmentType", "amendmentType", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_amendmentType", "amendmentType", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountStatus", "accountStatus", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_accountStatus", "accountStatus", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestorIsParty", "requestorIsParty", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_requestorIsParty", "requestorIsParty", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("mandatePermits", "mandatePermits", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_mandatePermits", "mandatePermits", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("channel", "channel", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_channel", "channel", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("screeningOutcome", "screeningOutcome", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_screeningOutcome", "screeningOutcome", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("inFlightAmendment", "inFlightAmendment", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_inFlightAmendment", "inFlightAmendment", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountIsJoint", "accountIsJoint", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_accountIsJoint", "accountIsJoint", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("riskBand", "riskBand", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_riskBand", "riskBand", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("addressVerified", "addressVerified", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_addressVerified", "addressVerified", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("rescreenOutcome", "rescreenOutcome", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_rescreenOutcome", "rescreenOutcome", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("allConsentsGranted", "allConsentsGranted", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_allConsentsGranted", "allConsentsGranted", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("nameChangeReason", "nameChangeReason", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_nameChangeReason", "nameChangeReason", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("otherPartyStatus", "otherPartyStatus", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_otherPartyStatus", "otherPartyStatus", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("jointLiabilities", "jointLiabilities", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_jointLiabilities", "jointLiabilities", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("remainingPartyEligible", "remainingPartyEligible", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake_remainingPartyEligible", "remainingPartyEligible", "java.lang.Object", null), null, null));
        workItemNode2.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("request", "request", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_intake", "request", "com.bank.amendments.model.AmendmentRequest", null), null, null));
        workItemNode2.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_intake", "request", "com.bank.amendments.model.AmendmentRequest", null)), new org.jbpm.workflow.core.impl.DataDefinition("request", "request", "java.lang.Object", null), null, null));
        workItemNode2.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_intake_accountId", "accountId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("accountId", "accountId", "java.lang.Object", null), null, null));
        workItemNode2.done();
        workItemNode2.metaData("UniqueId", "task_intake");
        workItemNode2.metaData("x", 166);
        workItemNode2.metaData("width", 140);
        workItemNode2.metaData("y", 80);
        workItemNode2.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode3 = factory.workItemNode(3);
        workItemNode3.name("Sanctions and PEP screening");
        workItemNode3.workName("SanctionsScreeningTask");
        workItemNode3.workParameter("NodeName", "Sanctions and PEP screening");
        workItemNode3.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("request", "request", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_scr", "request", "com.bank.amendments.model.AmendmentRequest", null), null, null));
        workItemNode3.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_scr", "request", "com.bank.amendments.model.AmendmentRequest", null)), new org.jbpm.workflow.core.impl.DataDefinition("request", "request", "java.lang.Object", null), null, null));
        workItemNode3.done();
        workItemNode3.metaData("UniqueId", "task_screening");
        workItemNode3.metaData("x", 376);
        workItemNode3.metaData("width", 140);
        workItemNode3.metaData("y", 80);
        workItemNode3.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.RuleSetNodeFactory<?> ruleSetNode4 = factory.ruleSetNode(4);
        ruleSetNode4.name("Assess admissibility");
        ruleSetNode4.decision("https://bank.com/dmn/amendments", "AmendmentAdmissibility", "Admissibility", () -> {
            return app.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://bank.com/dmn/amendments", "AmendmentAdmissibility");
        });
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_admissibility_0", "namespace", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("c5f25598-f4da-4806-a3a4-464b5e49da9d", "EXPRESSION (https://bank.com/dmn/amendments)", "java.lang.Object", "https://bank.com/dmn/amendments"), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_admissibility_0", "namespace", "java.lang.Object", null))), null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_admissibility_1", "model", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("36354a4d-e41a-4879-aab0-358a593a0b3e", "EXPRESSION (AmendmentAdmissibility)", "java.lang.Object", "AmendmentAdmissibility"), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_admissibility_1", "model", "java.lang.Object", null))), null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_admissibility_2", "decision", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("1d70acac-1155-4573-abf7-0f49aa108834", "EXPRESSION (Admissibility)", "java.lang.Object", "Admissibility"), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_admissibility_2", "decision", "java.lang.Object", null))), null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("amendmentType", "amendmentType", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_amendmentType", "amendmentType", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountStatus", "accountStatus", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_accountStatus", "accountStatus", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestorIsParty", "requestorIsParty", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_requestorIsParty", "requestorIsParty", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("mandatePermits", "mandatePermits", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_mandatePermits", "mandatePermits", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("channel", "channel", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_channel", "channel", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("screeningOutcome", "screeningOutcome", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_screeningOutcome", "screeningOutcome", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("inFlightAmendment", "inFlightAmendment", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_inFlightAmendment", "inFlightAmendment", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountIsJoint", "accountIsJoint", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_accountIsJoint", "accountIsJoint", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("riskBand", "riskBand", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_adm_riskBand", "riskBand", "java.lang.Object", null), null, null));
        ruleSetNode4.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_adm_result", "admissibility", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("admissibility", "admissibility", "java.lang.Object", null), null, null));
        ruleSetNode4.metaData("UniqueId", "task_admissibility");
        ruleSetNode4.metaData("x", 586);
        ruleSetNode4.metaData("width", 140);
        ruleSetNode4.metaData("y", 80);
        ruleSetNode4.metaData("height", 80);
        ruleSetNode4.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode5 = factory.workItemNode(5);
        workItemNode5.name("Derive execution plan");
        workItemNode5.workName("DerivePlanTask");
        workItemNode5.workParameter("NodeName", "Derive execution plan");
        workItemNode5.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("request", "request", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_derive_plan_request", "request", "java.lang.Object", null), null, null));
        workItemNode5.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("admissibility", "admissibility", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_derive_plan_admissibility", "admissibility", "java.lang.Object", null), null, null));
        workItemNode5.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_task_derive_plan_permittedCount", "permittedCount", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("permittedCount", "permittedCount", "java.lang.Object", null), null, null));
        workItemNode5.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_task_derive_plan_includesCoa", "includesCoa", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("includesCoa", "includesCoa", "java.lang.Object", null), null, null));
        workItemNode5.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_task_derive_plan_includesCon", "includesCon", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("includesCon", "includesCon", "java.lang.Object", null), null, null));
        workItemNode5.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_task_derive_plan_includesJointToSole", "includesJointToSole", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("includesJointToSole", "includesJointToSole", "java.lang.Object", null), null, null));
        workItemNode5.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_task_derive_plan_anyPermitted", "anyPermitted", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("anyPermitted", "anyPermitted", "java.lang.Object", null), null, null));
        workItemNode5.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_task_derive_plan_amendmentItems", "amendmentItems", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("amendmentItems", "amendmentItems", "java.lang.Object", null), null, null));
        workItemNode5.done();
        workItemNode5.metaData("UniqueId", "task_derive_plan");
        workItemNode5.metaData("x", 740);
        workItemNode5.metaData("width", 140);
        workItemNode5.metaData("y", 80);
        workItemNode5.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.RuleSetNodeFactory<?> ruleSetNode6 = factory.ruleSetNode(6);
        ruleSetNode6.name("Determine execution plan");
        ruleSetNode6.decision("https://bank.com/dmn/amendments", "AmendmentSequencing", null, () -> {
            return app.get(org.kie.kogito.decision.DecisionModels.class).getDecisionModel("https://bank.com/dmn/amendments", "AmendmentSequencing");
        });
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_sequencing_0", "namespace", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("8a04c929-7f1b-4414-baa4-b2a3d241a032", "EXPRESSION (https://bank.com/dmn/amendments)", "java.lang.Object", "https://bank.com/dmn/amendments"), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_sequencing_0", "namespace", "java.lang.Object", null))), null));
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_sequencing_1", "model", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("06175edb-2752-4142-bdc8-9b388cfe61a3", "EXPRESSION (AmendmentSequencing)", "java.lang.Object", "AmendmentSequencing"), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_sequencing_1", "model", "java.lang.Object", null))), null));
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_sequencing_2", "decision", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("ExecutionPlan", "ExecutionPlan", "java.lang.Object", null), new org.jbpm.workflow.core.impl.DataDefinition("_di_task_sequencing_2", "decision", "java.lang.Object", null))), null));
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("permittedCount", "permittedCount", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_seq_permittedCount", "permittedCount", "java.lang.Object", null), null, null));
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("includesJointToSole", "includesJointToSole", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_seq_includesJointToSole", "includesJointToSole", "java.lang.Object", null), null, null));
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("includesCon", "includesCon", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_seq_includesCon", "includesCon", "java.lang.Object", null), null, null));
        ruleSetNode6.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("includesCoa", "includesCoa", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_seq_includesCoa", "includesCoa", "java.lang.Object", null), null, null));
        ruleSetNode6.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_seq_result", "ExecutionPlan", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("ExecutionPlan", "ExecutionPlan", "java.lang.Object", null), null, null));
        ruleSetNode6.metaData("UniqueId", "task_sequencing");
        ruleSetNode6.metaData("x", 796);
        ruleSetNode6.metaData("width", 140);
        ruleSetNode6.metaData("y", 80);
        ruleSetNode6.metaData("height", 80);
        ruleSetNode6.done();
        org.jbpm.ruleflow.core.factory.SplitFactory<?> splitNode7 = factory.splitNode(7);
        splitNode7.name("Any permitted");
        splitNode7.type(2);
        splitNode7.metaData("UniqueId", "gw_any_permitted");
        splitNode7.metaData("x", 1006);
        splitNode7.metaData("width", 50);
        splitNode7.metaData("y", 80);
        splitNode7.metaData("Default", null);
        splitNode7.metaData("height", 50);
        splitNode7.constraint(8, "f6", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("anyPermitted = true"), 0, false);
        splitNode7.constraint(17, "f_refuse", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("anyPermitted = false"), 0, false);
        splitNode7.done();
        org.jbpm.ruleflow.core.factory.ForEachNodeFactory<?> forEachNode8 = factory.forEachNode(8);
        forEachNode8.name("Execute permitted amendments");
        forEachNode8.sequential(false);
        forEachNode8.metaData("UniqueId", "sub_dispatch");
        forEachNode8.metaData("x", 1126);
        forEachNode8.metaData("width", 682);
        forEachNode8.metaData("y", 80);
        forEachNode8.metaData("height", 420);
        forEachNode8.tempVariable("foreach_output", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.util.Collection.class));
        forEachNode8.variable("item", "item", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class));
        forEachNode8.collectionExpression("amendmentItems");
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode9 = forEachNode8.startNode(9);
        startNode9.name("Start");
        startNode9.interrupting(false);
        startNode9.metaData("UniqueId", "sub_start");
        startNode9.metaData("x", 45);
        startNode9.metaData("width", 36);
        startNode9.metaData("y", 60);
        startNode9.metaData("height", 36);
        startNode9.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode10 = forEachNode8.workItemNode(10);
        workItemNode10.name("Read amendment type");
        workItemNode10.workName("ReadItemDetailTask");
        workItemNode10.workParameter("NodeName", "Read amendment type");
        workItemNode10.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_sub_derive_type_item", "item", "java.lang.Object", null), null, null));
        workItemNode10.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_sub_derive_type_itemType", "itemType", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("itemType", "itemType", "java.lang.Object", null), null, null));
        workItemNode10.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_sub_derive_type_isCoa", "isCoa", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("isCoa", "isCoa", "java.lang.Object", null), null, null));
        workItemNode10.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_sub_derive_type_isCon", "isCon", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("isCon", "isCon", "java.lang.Object", null), null, null));
        workItemNode10.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_sub_derive_type_isJointToSole", "isJointToSole", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("isJointToSole", "isJointToSole", "java.lang.Object", null), null, null));
        workItemNode10.done();
        workItemNode10.metaData("UniqueId", "sub_derive_type");
        org.jbpm.ruleflow.core.factory.SplitFactory<?> splitNode11 = forEachNode8.splitNode(11);
        splitNode11.name("Amendment type");
        splitNode11.type(2);
        splitNode11.metaData("UniqueId", "gw_type");
        splitNode11.metaData("x", 151);
        splitNode11.metaData("width", 50);
        splitNode11.metaData("y", 60);
        splitNode11.metaData("Default", null);
        splitNode11.metaData("height", 50);
        splitNode11.constraint(13, "sf_con", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("isCon = true"), 0, false);
        splitNode11.constraint(12, "sf_coa", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("isCoa = true"), 0, false);
        splitNode11.constraint(14, "sf_jts", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("isJointToSole = true"), 0, false);
        splitNode11.done();
        org.jbpm.ruleflow.core.factory.SubProcessNodeFactory<?> subProcessNode12 = forEachNode8.subProcessNode(12);
        subProcessNode12.name("Change of address");
        subProcessNode12.processId("amendment_coa");
        subProcessNode12.processName("");
        subProcessNode12.waitForCompletion(true);
        subProcessNode12.independent(true);
        subProcessNode12.subProcessNode(new org.jbpm.workflow.core.node.SubProcessFactory<Amendment_coaModel>() {

            public Amendment_coaModel bind(org.kie.api.runtime.process.ProcessContext kcontext) {
                org.drools.bpmn2.Amendment_coaModel model = new org.drools.bpmn2.Amendment_coaModel();
                java.util.Map<java.lang.String, java.lang.Object> inputs = org.jbpm.workflow.core.impl.NodeIoHelper.processInputs((org.jbpm.workflow.instance.impl.NodeInstanceImpl) kcontext.getNodeInstance(), (java.lang.String name) -> {
                    return kcontext.getVariable(name);
                });
                model.update(inputs);
                return model;
            }

            public org.kie.kogito.process.ProcessInstance<Amendment_coaModel> createInstance(Amendment_coaModel model) {
                return processamendment_coa.createInstance(model);
            }

            public void unbind(org.kie.api.runtime.process.ProcessContext kcontext, Amendment_coaModel model) {
                java.util.Map<java.lang.String, java.lang.Object> outputs = new java.util.HashMap<java.lang.String, java.lang.Object>();
                org.jbpm.workflow.core.impl.NodeIoHelper.processOutputs((org.jbpm.workflow.instance.impl.NodeInstanceImpl) kcontext.getNodeInstance(), (java.lang.String name) -> {
                    return outputs.get(name);
                }, (java.lang.String name) -> {
                    return kcontext.getVariable(name);
                });
            }
        });
        subProcessNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_coa_item", "item", "java.lang.Object", null), null, null));
        subProcessNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountId", "accountId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_coa_accountId", "accountId", "java.lang.Object", null), null, null));
        subProcessNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_coa_requestId", "requestId", "java.lang.Object", null), null, null));
        subProcessNode12.metaData("UniqueId", "call_coa");
        subProcessNode12.metaData("x", 271);
        subProcessNode12.metaData("width", 140);
        subProcessNode12.metaData("y", 60);
        subProcessNode12.metaData("height", 80);
        subProcessNode12.done();
        org.jbpm.ruleflow.core.factory.SubProcessNodeFactory<?> subProcessNode13 = forEachNode8.subProcessNode(13);
        subProcessNode13.name("Change of name");
        subProcessNode13.processId("amendment_con");
        subProcessNode13.processName("");
        subProcessNode13.waitForCompletion(true);
        subProcessNode13.independent(true);
        subProcessNode13.subProcessNode(new org.jbpm.workflow.core.node.SubProcessFactory<Amendment_conModel>() {

            public Amendment_conModel bind(org.kie.api.runtime.process.ProcessContext kcontext) {
                org.drools.bpmn2.Amendment_conModel model = new org.drools.bpmn2.Amendment_conModel();
                java.util.Map<java.lang.String, java.lang.Object> inputs = org.jbpm.workflow.core.impl.NodeIoHelper.processInputs((org.jbpm.workflow.instance.impl.NodeInstanceImpl) kcontext.getNodeInstance(), (java.lang.String name) -> {
                    return kcontext.getVariable(name);
                });
                model.update(inputs);
                return model;
            }

            public org.kie.kogito.process.ProcessInstance<Amendment_conModel> createInstance(Amendment_conModel model) {
                return processamendment_con.createInstance(model);
            }

            public void unbind(org.kie.api.runtime.process.ProcessContext kcontext, Amendment_conModel model) {
                java.util.Map<java.lang.String, java.lang.Object> outputs = new java.util.HashMap<java.lang.String, java.lang.Object>();
                org.jbpm.workflow.core.impl.NodeIoHelper.processOutputs((org.jbpm.workflow.instance.impl.NodeInstanceImpl) kcontext.getNodeInstance(), (java.lang.String name) -> {
                    return outputs.get(name);
                }, (java.lang.String name) -> {
                    return kcontext.getVariable(name);
                });
            }
        });
        subProcessNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_con_item", "item", "java.lang.Object", null), null, null));
        subProcessNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountId", "accountId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_con_accountId", "accountId", "java.lang.Object", null), null, null));
        subProcessNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_con_requestId", "requestId", "java.lang.Object", null), null, null));
        subProcessNode13.metaData("UniqueId", "call_con");
        subProcessNode13.metaData("x", 271);
        subProcessNode13.metaData("width", 140);
        subProcessNode13.metaData("y", 185);
        subProcessNode13.metaData("height", 80);
        subProcessNode13.done();
        org.jbpm.ruleflow.core.factory.SubProcessNodeFactory<?> subProcessNode14 = forEachNode8.subProcessNode(14);
        subProcessNode14.name("Joint to sole");
        subProcessNode14.processId("amendment_joint_to_sole");
        subProcessNode14.processName("");
        subProcessNode14.waitForCompletion(true);
        subProcessNode14.independent(true);
        subProcessNode14.subProcessNode(new org.jbpm.workflow.core.node.SubProcessFactory<Amendment_joint_to_soleModel>() {

            public Amendment_joint_to_soleModel bind(org.kie.api.runtime.process.ProcessContext kcontext) {
                org.drools.bpmn2.Amendment_joint_to_soleModel model = new org.drools.bpmn2.Amendment_joint_to_soleModel();
                java.util.Map<java.lang.String, java.lang.Object> inputs = org.jbpm.workflow.core.impl.NodeIoHelper.processInputs((org.jbpm.workflow.instance.impl.NodeInstanceImpl) kcontext.getNodeInstance(), (java.lang.String name) -> {
                    return kcontext.getVariable(name);
                });
                model.update(inputs);
                return model;
            }

            public org.kie.kogito.process.ProcessInstance<Amendment_joint_to_soleModel> createInstance(Amendment_joint_to_soleModel model) {
                return processamendment_joint_to_sole.createInstance(model);
            }

            public void unbind(org.kie.api.runtime.process.ProcessContext kcontext, Amendment_joint_to_soleModel model) {
                java.util.Map<java.lang.String, java.lang.Object> outputs = new java.util.HashMap<java.lang.String, java.lang.Object>();
                org.jbpm.workflow.core.impl.NodeIoHelper.processOutputs((org.jbpm.workflow.instance.impl.NodeInstanceImpl) kcontext.getNodeInstance(), (java.lang.String name) -> {
                    return outputs.get(name);
                }, (java.lang.String name) -> {
                    return kcontext.getVariable(name);
                });
            }
        });
        subProcessNode14.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_jts_item", "item", "java.lang.Object", null), null, null));
        subProcessNode14.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountId", "accountId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_jts_accountId", "accountId", "java.lang.Object", null), null, null));
        subProcessNode14.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_call_jts_requestId", "requestId", "java.lang.Object", null), null, null));
        subProcessNode14.metaData("UniqueId", "call_jts");
        subProcessNode14.metaData("x", 271);
        subProcessNode14.metaData("width", 140);
        subProcessNode14.metaData("y", 310);
        subProcessNode14.metaData("height", 80);
        subProcessNode14.done();
        org.jbpm.ruleflow.core.factory.JoinFactory<?> joinNode15 = forEachNode8.joinNode(15);
        joinNode15.name("Join");
        joinNode15.type(2);
        joinNode15.metaData("UniqueId", "gw_type_join");
        joinNode15.metaData("x", 481);
        joinNode15.metaData("width", 50);
        joinNode15.metaData("y", 60);
        joinNode15.metaData("height", 50);
        joinNode15.done();
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode16 = forEachNode8.endNode(16);
        endNode16.name("End");
        endNode16.terminate(false);
        endNode16.metaData("UniqueId", "sub_end");
        endNode16.metaData("x", 601);
        endNode16.metaData("width", 36);
        endNode16.metaData("y", 60);
        endNode16.metaData("height", 36);
        endNode16.done();
        forEachNode8.connection(9, 10, "sf1");
        forEachNode8.connection(10, 11, "sf1b");
        forEachNode8.connection(11, 12, "sf_coa");
        forEachNode8.connection(11, 13, "sf_con");
        forEachNode8.connection(11, 14, "sf_jts");
        forEachNode8.connection(12, 15, "sf_coa_out");
        forEachNode8.connection(13, 15, "sf_con_out");
        forEachNode8.connection(14, 15, "sf_jts_out");
        forEachNode8.connection(15, 16, "sf_end");
        forEachNode8.done();
        org.jbpm.ruleflow.core.factory.JoinFactory<?> joinNode17 = factory.joinNode(17);
        joinNode17.name("Join outcomes");
        joinNode17.type(2);
        joinNode17.metaData("UniqueId", "gw_outcome_join");
        joinNode17.metaData("x", 1878);
        joinNode17.metaData("width", 50);
        joinNode17.metaData("y", 80);
        joinNode17.metaData("height", 50);
        joinNode17.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode18 = factory.workItemNode(18);
        workItemNode18.name("Aggregate outcomes and notify customer");
        workItemNode18.workName("CustomerNotificationTask");
        workItemNode18.workParameter("NodeName", "Aggregate outcomes and notify customer");
        workItemNode18.done();
        workItemNode18.metaData("UniqueId", "task_aggregate");
        workItemNode18.metaData("x", 1998);
        workItemNode18.metaData("width", 140);
        workItemNode18.metaData("y", 80);
        workItemNode18.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode19 = factory.endNode(19);
        endNode19.name("Request closed");
        endNode19.terminate(false);
        endNode19.metaData("UniqueId", "end");
        endNode19.metaData("x", 2208);
        endNode19.metaData("width", 36);
        endNode19.metaData("y", 80);
        endNode19.metaData("height", 36);
        endNode19.done();
        org.jbpm.ruleflow.core.factory.EventSubProcessNodeFactory<?> eventSubProcessNode20 = factory.eventSubProcessNode(20);
        eventSubProcessNode20.name("Extra document received");
        eventSubProcessNode20.metaData("UniqueId", "evsub_document");
        eventSubProcessNode20.metaData("x", 60);
        eventSubProcessNode20.metaData("width", 442);
        eventSubProcessNode20.metaData("y", 840);
        eventSubProcessNode20.metaData("height", 170);
        eventSubProcessNode20.keepActive(true);
        eventSubProcessNode20.event("Message-ExtraDocumentSubmitted");
        eventSubProcessNode20.autoComplete(true);
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode21 = eventSubProcessNode20.startNode(21);
        startNode21.name("Document submitted");
        startNode21.interrupting(false);
        startNode21.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_ev_doc_start", "_do_ev_doc_start", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null), null, null));
        startNode21.metaData("TriggerMapping", "requestId");
        startNode21.metaData("UniqueId", "ev_doc_start");
        startNode21.metaData("TriggerType", "ConsumeMessage");
        startNode21.metaData("EventType", "message");
        startNode21.metaData("TriggerRef", "ExtraDocumentSubmitted");
        startNode21.metaData("x", 45);
        startNode21.metaData("width", 36);
        startNode21.metaData("y", 60);
        startNode21.metaData("MappingVariable", "requestId");
        startNode21.metaData("TriggerMappingInput", "_do_ev_doc_start");
        startNode21.metaData("MessageType", "String");
        startNode21.metaData("height", 36);
        startNode21.done();
        startNode21.trigger("ExtraDocumentSubmitted", java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_ev_doc_start", "_do_ev_doc_start", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null), null, null)));
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode22 = eventSubProcessNode20.workItemNode(22);
        workItemNode22.name("Attach and verify document");
        workItemNode22.workName("DocumentVerificationTask");
        workItemNode22.workParameter("NodeName", "Attach and verify document");
        workItemNode22.done();
        workItemNode22.metaData("UniqueId", "ev_doc_task");
        workItemNode22.metaData("x", 151);
        workItemNode22.metaData("width", 140);
        workItemNode22.metaData("y", 60);
        workItemNode22.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode23 = eventSubProcessNode20.endNode(23);
        endNode23.name("End");
        endNode23.terminate(false);
        endNode23.metaData("UniqueId", "ev_doc_end");
        endNode23.metaData("x", 361);
        endNode23.metaData("width", 36);
        endNode23.metaData("y", 60);
        endNode23.metaData("height", 36);
        endNode23.done();
        eventSubProcessNode20.connection(21, 22, "ed1");
        eventSubProcessNode20.connection(22, 23, "ed2");
        eventSubProcessNode20.done();
        org.jbpm.ruleflow.core.factory.EventSubProcessNodeFactory<?> eventSubProcessNode24 = factory.eventSubProcessNode(24);
        eventSubProcessNode24.name("Additional verification");
        eventSubProcessNode24.metaData("UniqueId", "evsub_verification");
        eventSubProcessNode24.metaData("x", 60);
        eventSubProcessNode24.metaData("width", 442);
        eventSubProcessNode24.metaData("y", 1300);
        eventSubProcessNode24.metaData("height", 170);
        eventSubProcessNode24.keepActive(true);
        eventSubProcessNode24.event("Message-AdditionalVerificationRequired");
        eventSubProcessNode24.autoComplete(true);
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode25 = eventSubProcessNode24.startNode(25);
        startNode25.name("Verification required");
        startNode25.interrupting(false);
        startNode25.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_ev_ver_start", "_do_ev_ver_start", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null), null, null));
        startNode25.metaData("TriggerMapping", "requestId");
        startNode25.metaData("UniqueId", "ev_ver_start");
        startNode25.metaData("TriggerType", "ConsumeMessage");
        startNode25.metaData("EventType", "message");
        startNode25.metaData("TriggerRef", "AdditionalVerificationRequired");
        startNode25.metaData("x", 45);
        startNode25.metaData("width", 36);
        startNode25.metaData("y", 60);
        startNode25.metaData("MappingVariable", "requestId");
        startNode25.metaData("TriggerMappingInput", "_do_ev_ver_start");
        startNode25.metaData("MessageType", "String");
        startNode25.metaData("height", 36);
        startNode25.done();
        startNode25.trigger("AdditionalVerificationRequired", java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_ev_ver_start", "_do_ev_ver_start", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null), null, null)));
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode26 = eventSubProcessNode24.humanTaskNode(26);
        humanTaskNode26.name("Complete additional verification");
        humanTaskNode26.workParameter("NodeName", "Complete additional verification");
        humanTaskNode26.workParameter("ActorId", "amendments-verification");
        humanTaskNode26.done();
        humanTaskNode26.metaData("UniqueId", "ev_ver_task");
        humanTaskNode26.metaData("x", 151);
        humanTaskNode26.metaData("width", 140);
        humanTaskNode26.metaData("y", 60);
        humanTaskNode26.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode27 = eventSubProcessNode24.endNode(27);
        endNode27.name("End");
        endNode27.terminate(false);
        endNode27.metaData("UniqueId", "ev_ver_end");
        endNode27.metaData("x", 361);
        endNode27.metaData("width", 36);
        endNode27.metaData("y", 60);
        endNode27.metaData("height", 36);
        endNode27.done();
        eventSubProcessNode24.connection(25, 26, "ev1");
        eventSubProcessNode24.connection(26, 27, "ev2");
        eventSubProcessNode24.done();
        org.jbpm.ruleflow.core.factory.EventSubProcessNodeFactory<?> eventSubProcessNode28 = factory.eventSubProcessNode(28);
        eventSubProcessNode28.name("Customer cancellation");
        eventSubProcessNode28.metaData("UniqueId", "evsub_cancel");
        eventSubProcessNode28.metaData("x", 60);
        eventSubProcessNode28.metaData("width", 442);
        eventSubProcessNode28.metaData("y", 610);
        eventSubProcessNode28.metaData("height", 170);
        eventSubProcessNode28.keepActive(true);
        eventSubProcessNode28.event("Message-AmendmentCancelled");
        eventSubProcessNode28.autoComplete(true);
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode29 = eventSubProcessNode28.startNode(29);
        startNode29.name("Cancellation received");
        startNode29.interrupting(true);
        startNode29.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_ev_can_start", "_do_ev_can_start", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null), null, null));
        startNode29.metaData("TriggerMapping", "requestId");
        startNode29.metaData("UniqueId", "ev_can_start");
        startNode29.metaData("TriggerType", "ConsumeMessage");
        startNode29.metaData("EventType", "message");
        startNode29.metaData("TriggerRef", "AmendmentCancelled");
        startNode29.metaData("x", 45);
        startNode29.metaData("width", 36);
        startNode29.metaData("y", 60);
        startNode29.metaData("MappingVariable", "requestId");
        startNode29.metaData("TriggerMappingInput", "_do_ev_can_start");
        startNode29.metaData("MessageType", "String");
        startNode29.metaData("height", 36);
        startNode29.done();
        startNode29.trigger("AmendmentCancelled", java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_ev_can_start", "_do_ev_can_start", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null), null, null)));
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode30 = eventSubProcessNode28.workItemNode(30);
        workItemNode30.name("Roll back and close request");
        workItemNode30.workName("RequestCancellationTask");
        workItemNode30.workParameter("NodeName", "Roll back and close request");
        workItemNode30.done();
        workItemNode30.metaData("UniqueId", "ev_can_task");
        workItemNode30.metaData("x", 151);
        workItemNode30.metaData("width", 140);
        workItemNode30.metaData("y", 60);
        workItemNode30.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode31 = eventSubProcessNode28.endNode(31);
        endNode31.name("Request cancelled");
        endNode31.terminate(true);
        endNode31.metaData("UniqueId", "ev_can_end");
        endNode31.metaData("x", 361);
        endNode31.metaData("width", 36);
        endNode31.metaData("y", 60);
        endNode31.metaData("height", 36);
        endNode31.done();
        eventSubProcessNode28.connection(29, 30, "ec1");
        eventSubProcessNode28.connection(30, 31, "ec2");
        eventSubProcessNode28.done();
        org.jbpm.ruleflow.core.factory.EventSubProcessNodeFactory<?> eventSubProcessNode32 = factory.eventSubProcessNode(32);
        eventSubProcessNode32.name("SLA escalation");
        eventSubProcessNode32.metaData("UniqueId", "evsub_sla");
        eventSubProcessNode32.metaData("x", 60);
        eventSubProcessNode32.metaData("width", 442);
        eventSubProcessNode32.metaData("y", 1070);
        eventSubProcessNode32.metaData("height", 170);
        eventSubProcessNode32.keepActive(true);
        eventSubProcessNode32.event("SlaBreached");
        eventSubProcessNode32.autoComplete(true);
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode33 = eventSubProcessNode32.startNode(33);
        startNode33.name("SLA breached");
        startNode33.interrupting(false);
        startNode33.metaData("UniqueId", "ev_sla_start");
        startNode33.metaData("TriggerType", "Signal");
        startNode33.metaData("x", 45);
        startNode33.metaData("width", 36);
        startNode33.metaData("y", 60);
        startNode33.metaData("TriggerRef", "SlaBreached");
        startNode33.metaData("MessageType", "SlaBreached");
        startNode33.metaData("height", 36);
        startNode33.done();
        startNode33.trigger("SlaBreached", java.util.Arrays.asList());
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode34 = eventSubProcessNode32.workItemNode(34);
        workItemNode34.name("Escalate to operations");
        workItemNode34.workName("SlaEscalationTask");
        workItemNode34.workParameter("NodeName", "Escalate to operations");
        workItemNode34.done();
        workItemNode34.metaData("UniqueId", "ev_sla_task");
        workItemNode34.metaData("x", 151);
        workItemNode34.metaData("width", 140);
        workItemNode34.metaData("y", 60);
        workItemNode34.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode35 = eventSubProcessNode32.endNode(35);
        endNode35.name("End");
        endNode35.terminate(false);
        endNode35.metaData("UniqueId", "ev_sla_end");
        endNode35.metaData("x", 361);
        endNode35.metaData("width", 36);
        endNode35.metaData("y", 60);
        endNode35.metaData("height", 36);
        endNode35.done();
        eventSubProcessNode32.connection(33, 34, "es1");
        eventSubProcessNode32.connection(34, 35, "es2");
        eventSubProcessNode32.done();
        factory.connection(1, 2, "f1");
        factory.connection(2, 3, "f2");
        factory.connection(3, 4, "f3");
        factory.connection(4, 5, "f4");
        factory.connection(5, 6, "f4b");
        factory.connection(6, 7, "f5");
        factory.connection(7, 8, "f6");
        factory.connection(7, 17, "f_refuse");
        factory.connection(8, 17, "f7");
        factory.connection(17, 18, "f_join_out");
        factory.connection(18, 19, "f8");
        factory.validate();
        return factory.getProcess();
    }

    protected void registerListeners() {
        services.getSignalManager().addEventListener("amendment_coa", completionEventListener);
        services.getSignalManager().addEventListener("amendment_con", completionEventListener);
        services.getSignalManager().addEventListener("amendment_joint_to_sole", completionEventListener);
    }
}
