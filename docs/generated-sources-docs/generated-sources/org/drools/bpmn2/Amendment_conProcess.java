package org.drools.bpmn2;

import org.drools.bpmn2.Amendment_conModel;
import org.kie.api.definition.process.Process;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.jbpm.process.core.datatype.impl.type.ObjectDataType;
import org.drools.core.util.KieFunctions;
import org.jbpm.process.core.datatype.impl.type.UndefinedDataType;
import org.jbpm.process.core.datatype.impl.type.StringDataType;

@org.springframework.stereotype.Component("amendment_con")
public class Amendment_conProcess extends org.kie.kogito.process.impl.AbstractProcess<org.drools.bpmn2.Amendment_conModel> {

    @org.springframework.beans.factory.annotation.Autowired()
    public Amendment_conProcess(org.kie.kogito.app.Application app, org.kie.kogito.correlation.CorrelationService correlations) {
        super(app, java.util.Arrays.asList(), correlations);
        activate();
    }

    public Amendment_conProcess() {
    }

    @Override()
    public org.drools.bpmn2.Amendment_conProcessInstance createInstance(org.drools.bpmn2.Amendment_conModel value) {
        return new org.drools.bpmn2.Amendment_conProcessInstance(this, value, this.createProcessRuntime());
    }

    public org.drools.bpmn2.Amendment_conProcessInstance createInstance(java.lang.String businessKey, org.drools.bpmn2.Amendment_conModel value) {
        return new org.drools.bpmn2.Amendment_conProcessInstance(this, value, businessKey, this.createProcessRuntime());
    }

    public org.drools.bpmn2.Amendment_conProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.correlation.CompositeCorrelation correlation, org.drools.bpmn2.Amendment_conModel value) {
        return new org.drools.bpmn2.Amendment_conProcessInstance(this, value, businessKey, this.createProcessRuntime(), correlation);
    }

    @Override()
    public org.drools.bpmn2.Amendment_conModel createModel() {
        return new org.drools.bpmn2.Amendment_conModel();
    }

    public org.drools.bpmn2.Amendment_conProcessInstance createInstance(org.kie.kogito.Model value) {
        return this.createInstance((org.drools.bpmn2.Amendment_conModel) value);
    }

    public org.drools.bpmn2.Amendment_conProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.Model value) {
        return this.createInstance(businessKey, (org.drools.bpmn2.Amendment_conModel) value);
    }

    public org.drools.bpmn2.Amendment_conProcessInstance createInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.drools.bpmn2.Amendment_conProcessInstance(this, this.createModel(), this.createProcessRuntime(), wpi);
    }

    public org.drools.bpmn2.Amendment_conProcessInstance createReadOnlyInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.drools.bpmn2.Amendment_conProcessInstance(this, this.createModel(), wpi);
    }

    protected org.kie.api.definition.process.Process process() {
        RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("amendment_con", true);
        factory.variable("rescreenOutcome", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("rescreenClear", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("accountId", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("item", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(com.bank.amendments.model.AmendmentItem.class), null, "customTags", "input");
        factory.variable("requestId", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.name("Change of Name");
        factory.packageName("org.drools.bpmn2");
        factory.dynamic(false);
        factory.version("1.0");
        factory.type("BPMN");
        factory.visibility("Public");
        factory.metaData("TargetNamespace", "https://bank.com/bpmn/amendments");
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode1 = factory.startNode(1);
        startNode1.name("Start");
        startNode1.interrupting(false);
        startNode1.metaData("UniqueId", "n_start");
        startNode1.metaData("x", 60);
        startNode1.metaData("width", 36);
        startNode1.metaData("y", 330);
        startNode1.metaData("height", 36);
        startNode1.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode2 = factory.workItemNode(2);
        workItemNode2.name("Determine required evidence");
        workItemNode2.workName("EvidenceRequirementTask");
        workItemNode2.workParameter("NodeName", "Determine required evidence");
        workItemNode2.done();
        workItemNode2.metaData("UniqueId", "n_evidence_rule");
        workItemNode2.metaData("x", 270);
        workItemNode2.metaData("width", 140);
        workItemNode2.metaData("y", 161);
        workItemNode2.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode3 = factory.humanTaskNode(3);
        humanTaskNode3.name("Collect and verify name evidence");
        humanTaskNode3.workParameter("NodeName", "Collect and verify name evidence");
        humanTaskNode3.workParameter("ActorId", "amendments-ops");
        humanTaskNode3.done();
        humanTaskNode3.metaData("UniqueId", "n_collect");
        humanTaskNode3.metaData("x", 480);
        humanTaskNode3.metaData("width", 140);
        humanTaskNode3.metaData("y", 80);
        humanTaskNode3.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.BoundaryEventNodeFactory<?> boundaryEventNode4 = factory.boundaryEventNode(4);
        boundaryEventNode4.name("Evidence SLA");
        boundaryEventNode4.eventType("Timer-n_collect-PT72H-4");
        boundaryEventNode4.attachedTo("n_collect");
        boundaryEventNode4.scope(null);
        boundaryEventNode4.metaData("UniqueId", "n_collect_timer");
        boundaryEventNode4.metaData("EventType", "timer");
        boundaryEventNode4.metaData("x", 602);
        boundaryEventNode4.metaData("width", 36);
        boundaryEventNode4.metaData("y", 142);
        boundaryEventNode4.metaData("AttachedTo", "n_collect");
        boundaryEventNode4.metaData("TimeDuration", "PT72H");
        boundaryEventNode4.metaData("CancelActivity", false);
        boundaryEventNode4.metaData("height", 36);
        boundaryEventNode4.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode5 = factory.workItemNode(5);
        workItemNode5.name("Send evidence reminder");
        workItemNode5.workName("EvidenceReminderTask");
        workItemNode5.workParameter("NodeName", "Send evidence reminder");
        workItemNode5.done();
        workItemNode5.metaData("UniqueId", "n_remind");
        workItemNode5.metaData("x", 60);
        workItemNode5.metaData("width", 140);
        workItemNode5.metaData("y", 205);
        workItemNode5.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode6 = factory.endNode(6);
        endNode6.name("End");
        endNode6.terminate(false);
        endNode6.metaData("UniqueId", "n_remind_end");
        endNode6.metaData("x", 270);
        endNode6.metaData("width", 36);
        endNode6.metaData("y", 286);
        endNode6.metaData("height", 36);
        endNode6.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode7 = factory.workItemNode(7);
        workItemNode7.name("Re-screen under new name");
        workItemNode7.workName("SanctionsScreeningTask");
        workItemNode7.workParameter("NodeName", "Re-screen under new name");
        workItemNode7.done();
        workItemNode7.metaData("UniqueId", "n_rescreen");
        workItemNode7.metaData("x", 690);
        workItemNode7.metaData("width", 140);
        workItemNode7.metaData("y", 80);
        workItemNode7.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode8 = factory.workItemNode(8);
        workItemNode8.name("Read screening outcome");
        workItemNode8.workName("ReadItemDetailTask");
        workItemNode8.workParameter("NodeName", "Read screening outcome");
        workItemNode8.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_n_derive_item", "item", "java.lang.Object", null), null, null));
        workItemNode8.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_n_derive_rescreenOutcome", "rescreenOutcome", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("rescreenOutcome", "rescreenOutcome", "java.lang.Object", null), null, null));
        workItemNode8.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_n_derive_rescreenClear", "rescreenClear", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("rescreenClear", "rescreenClear", "java.lang.Object", null), null, null));
        workItemNode8.done();
        workItemNode8.metaData("UniqueId", "n_derive");
        org.jbpm.ruleflow.core.factory.SplitFactory<?> splitNode9 = factory.splitNode(9);
        splitNode9.name("Screening clear");
        splitNode9.type(2);
        splitNode9.metaData("UniqueId", "n_gw_screen");
        splitNode9.metaData("x", 900);
        splitNode9.metaData("width", 50);
        splitNode9.metaData("y", 80);
        splitNode9.metaData("Default", null);
        splitNode9.metaData("height", 50);
        splitNode9.constraint(11, "nf_clear", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("rescreenClear = true"), 0, false);
        splitNode9.constraint(10, "nf_refer", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("rescreenClear = false"), 0, false);
        splitNode9.done();
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode10 = factory.humanTaskNode(10);
        humanTaskNode10.name("Financial crime disposition");
        humanTaskNode10.workParameter("NodeName", "Financial crime disposition");
        humanTaskNode10.workParameter("ActorId", "financial-crime");
        humanTaskNode10.done();
        humanTaskNode10.metaData("UniqueId", "n_refer");
        humanTaskNode10.metaData("x", 1020);
        humanTaskNode10.metaData("width", 140);
        humanTaskNode10.metaData("y", 80);
        humanTaskNode10.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.JoinFactory<?> joinNode11 = factory.joinNode(11);
        joinNode11.name("Join");
        joinNode11.type(2);
        joinNode11.metaData("UniqueId", "n_gw_join");
        joinNode11.metaData("x", 1230);
        joinNode11.metaData("width", 50);
        joinNode11.metaData("y", 80);
        joinNode11.metaData("height", 50);
        joinNode11.done();
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode12 = factory.humanTaskNode(12);
        humanTaskNode12.name("Maker checker approval");
        humanTaskNode12.workParameter("NodeName", "Maker checker approval");
        humanTaskNode12.workParameter("ActorId", "amendments-checker");
        humanTaskNode12.done();
        humanTaskNode12.metaData("UniqueId", "n_approve");
        humanTaskNode12.metaData("x", 1350);
        humanTaskNode12.metaData("width", 140);
        humanTaskNode12.metaData("y", 80);
        humanTaskNode12.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode13 = factory.workItemNode(13);
        workItemNode13.name("Apply name in core banking");
        workItemNode13.workName("CoreBankingTask");
        workItemNode13.workParameter("NodeName", "Apply name in core banking");
        workItemNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_n_apply_item", "item", "java.lang.Object", null), null, null));
        workItemNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_n_apply_operation", "operation", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("e37ea8b5-e79a-4498-851e-a95fc8f2687b", "EXPRESSION (APPLY_NAME)", "java.lang.Object", "APPLY_NAME"), new org.jbpm.workflow.core.impl.DataDefinition("_di_n_apply_operation", "operation", "java.lang.Object", null))), null));
        workItemNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountId", "accountId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_n_apply_accountId", "accountId", "java.lang.Object", null), null, null));
        workItemNode13.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_n_apply_idempotencyKey", "idempotencyKey", "java.lang.Object", null), null, null));
        workItemNode13.done();
        workItemNode13.metaData("UniqueId", "n_apply");
        workItemNode13.metaData("x", 1560);
        workItemNode13.metaData("width", 140);
        workItemNode13.metaData("y", 80);
        workItemNode13.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.BoundaryEventNodeFactory<?> boundaryEventNode14 = factory.boundaryEventNode(14);
        boundaryEventNode14.name("Core failure");
        boundaryEventNode14.eventType("Error-n_apply-CORE_BANKING_FAILURE");
        boundaryEventNode14.attachedTo("n_apply");
        boundaryEventNode14.scope(null);
        boundaryEventNode14.metaData("UniqueId", "n_apply_error");
        boundaryEventNode14.metaData("EventType", "error");
        boundaryEventNode14.metaData("ErrorStructureRef", null);
        boundaryEventNode14.metaData("ErrorEvent", "CORE_BANKING_FAILURE");
        boundaryEventNode14.metaData("x", 1682);
        boundaryEventNode14.metaData("width", 36);
        boundaryEventNode14.metaData("y", 142);
        boundaryEventNode14.metaData("AttachedTo", "n_apply");
        boundaryEventNode14.metaData("HasErrorEvent", true);
        boundaryEventNode14.metaData("height", 36);
        boundaryEventNode14.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode15 = factory.workItemNode(15);
        workItemNode15.name("Record failure and refer");
        workItemNode15.workName("FailureRecordingTask");
        workItemNode15.workParameter("NodeName", "Record failure and refer");
        workItemNode15.done();
        workItemNode15.metaData("UniqueId", "n_fail");
        workItemNode15.metaData("x", 60);
        workItemNode15.metaData("width", 140);
        workItemNode15.metaData("y", 80);
        workItemNode15.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode16 = factory.endNode(16);
        endNode16.name("Failed");
        endNode16.terminate(false);
        endNode16.metaData("UniqueId", "n_end_failed");
        endNode16.metaData("x", 270);
        endNode16.metaData("width", 36);
        endNode16.metaData("y", 80);
        endNode16.metaData("height", 36);
        endNode16.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode17 = factory.workItemNode(17);
        workItemNode17.name("Trigger card reissue");
        workItemNode17.workName("CardReissueTask");
        workItemNode17.workParameter("NodeName", "Trigger card reissue");
        workItemNode17.done();
        workItemNode17.metaData("UniqueId", "n_reissue");
        workItemNode17.metaData("x", 1770);
        workItemNode17.metaData("width", 140);
        workItemNode17.metaData("y", 80);
        workItemNode17.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode18 = factory.endNode(18);
        endNode18.name("Applied");
        endNode18.terminate(false);
        endNode18.metaData("UniqueId", "n_end");
        endNode18.metaData("x", 1980);
        endNode18.metaData("width", 36);
        endNode18.metaData("y", 80);
        endNode18.metaData("height", 36);
        endNode18.done();
        factory.errorExceptionHandler("Error-n_apply-CORE_BANKING_FAILURE", "CORE_BANKING_FAILURE", null);
        factory.connection(1, 2, "nf1");
        factory.connection(2, 3, "nf2");
        factory.connection(4, 5, "nf_timer");
        factory.connection(5, 6, "nf_remind_out");
        factory.connection(3, 7, "nf3");
        factory.connection(7, 8, "nf4");
        factory.connection(8, 9, "nf4b");
        factory.connection(9, 10, "nf_refer");
        factory.connection(9, 11, "nf_clear");
        factory.connection(10, 11, "nf_refer_out");
        factory.connection(11, 12, "nf5");
        factory.connection(12, 13, "nf6");
        factory.connection(14, 15, "nf_err");
        factory.connection(15, 16, "nf_err_out");
        factory.connection(13, 17, "nf7");
        factory.connection(17, 18, "nf8");
        factory.validate();
        return factory.getProcess();
    }
}
