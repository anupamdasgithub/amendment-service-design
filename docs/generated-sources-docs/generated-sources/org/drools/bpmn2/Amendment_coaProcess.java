package org.drools.bpmn2;

import org.drools.bpmn2.Amendment_coaModel;
import org.kie.api.definition.process.Process;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;
import org.jbpm.process.core.datatype.impl.type.ObjectDataType;
import org.drools.core.util.KieFunctions;
import org.jbpm.process.core.datatype.impl.type.UndefinedDataType;
import org.jbpm.process.core.datatype.impl.type.StringDataType;

@org.springframework.stereotype.Component("amendment_coa")
public class Amendment_coaProcess extends org.kie.kogito.process.impl.AbstractProcess<org.drools.bpmn2.Amendment_coaModel> {

    @org.springframework.beans.factory.annotation.Autowired()
    public Amendment_coaProcess(org.kie.kogito.app.Application app, org.kie.kogito.correlation.CorrelationService correlations) {
        super(app, java.util.Arrays.asList(), correlations);
        activate();
    }

    public Amendment_coaProcess() {
    }

    @Override()
    public org.drools.bpmn2.Amendment_coaProcessInstance createInstance(org.drools.bpmn2.Amendment_coaModel value) {
        return new org.drools.bpmn2.Amendment_coaProcessInstance(this, value, this.createProcessRuntime());
    }

    public org.drools.bpmn2.Amendment_coaProcessInstance createInstance(java.lang.String businessKey, org.drools.bpmn2.Amendment_coaModel value) {
        return new org.drools.bpmn2.Amendment_coaProcessInstance(this, value, businessKey, this.createProcessRuntime());
    }

    public org.drools.bpmn2.Amendment_coaProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.correlation.CompositeCorrelation correlation, org.drools.bpmn2.Amendment_coaModel value) {
        return new org.drools.bpmn2.Amendment_coaProcessInstance(this, value, businessKey, this.createProcessRuntime(), correlation);
    }

    @Override()
    public org.drools.bpmn2.Amendment_coaModel createModel() {
        return new org.drools.bpmn2.Amendment_coaModel();
    }

    public org.drools.bpmn2.Amendment_coaProcessInstance createInstance(org.kie.kogito.Model value) {
        return this.createInstance((org.drools.bpmn2.Amendment_coaModel) value);
    }

    public org.drools.bpmn2.Amendment_coaProcessInstance createInstance(java.lang.String businessKey, org.kie.kogito.Model value) {
        return this.createInstance(businessKey, (org.drools.bpmn2.Amendment_coaModel) value);
    }

    public org.drools.bpmn2.Amendment_coaProcessInstance createInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.drools.bpmn2.Amendment_coaProcessInstance(this, this.createModel(), this.createProcessRuntime(), wpi);
    }

    public org.drools.bpmn2.Amendment_coaProcessInstance createReadOnlyInstance(org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        return new org.drools.bpmn2.Amendment_coaProcessInstance(this, this.createModel(), wpi);
    }

    protected org.kie.api.definition.process.Process process() {
        RuleFlowProcessFactory factory = RuleFlowProcessFactory.createProcess("amendment_coa", true);
        factory.variable("addressVerified", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("accountId", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.Object.class), null, "customTags", null);
        factory.variable("item", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(com.bank.amendments.model.AmendmentItem.class), null, "customTags", "input");
        factory.variable("requestId", org.jbpm.process.core.datatype.DataTypeResolver.fromClass(java.lang.String.class), null, "customTags", "input");
        factory.name("Change of Address");
        factory.packageName("org.drools.bpmn2");
        factory.dynamic(false);
        factory.version("1.0");
        factory.type("BPMN");
        factory.visibility("Public");
        factory.metaData("TargetNamespace", "https://bank.com/bpmn/amendments");
        org.jbpm.ruleflow.core.factory.StartNodeFactory<?> startNode1 = factory.startNode(1);
        startNode1.name("Start");
        startNode1.interrupting(false);
        startNode1.metaData("UniqueId", "c_start");
        startNode1.metaData("x", 60);
        startNode1.metaData("width", 36);
        startNode1.metaData("y", 330);
        startNode1.metaData("height", 36);
        startNode1.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode2 = factory.workItemNode(2);
        workItemNode2.name("Validate and normalise address");
        workItemNode2.workName("AddressValidationTask");
        workItemNode2.workParameter("NodeName", "Validate and normalise address");
        workItemNode2.done();
        workItemNode2.metaData("UniqueId", "c_validate");
        workItemNode2.metaData("x", 270);
        workItemNode2.metaData("width", 140);
        workItemNode2.metaData("y", 242);
        workItemNode2.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode3 = factory.workItemNode(3);
        workItemNode3.name("Third party address verification");
        workItemNode3.workName("AddressVerificationTask");
        workItemNode3.workParameter("NodeName", "Third party address verification");
        workItemNode3.done();
        workItemNode3.metaData("UniqueId", "c_verify");
        workItemNode3.metaData("x", 480);
        workItemNode3.metaData("width", 140);
        workItemNode3.metaData("y", 80);
        workItemNode3.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode4 = factory.workItemNode(4);
        workItemNode4.name("Read verification result");
        workItemNode4.workName("ReadItemDetailTask");
        workItemNode4.workParameter("NodeName", "Read verification result");
        workItemNode4.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_c_derive_item", "item", "java.lang.Object", null), null, null));
        workItemNode4.mapDataOutputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("_do_c_derive_addressVerified", "addressVerified", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("addressVerified", "addressVerified", "java.lang.Object", null), null, null));
        workItemNode4.done();
        workItemNode4.metaData("UniqueId", "c_derive");
        org.jbpm.ruleflow.core.factory.SplitFactory<?> splitNode5 = factory.splitNode(5);
        splitNode5.name("Verified");
        splitNode5.type(2);
        splitNode5.metaData("UniqueId", "c_gw_verified");
        splitNode5.metaData("x", 690);
        splitNode5.metaData("width", 50);
        splitNode5.metaData("y", 80);
        splitNode5.metaData("Default", null);
        splitNode5.metaData("height", 50);
        splitNode5.constraint(6, "cf_evidence", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("addressVerified = false"), 0, false);
        splitNode5.constraint(10, "cf_ok", "DROOLS_DEFAULT", "FEEL", new org.jbpm.bpmn2.feel.FeelReturnValueEvaluator("addressVerified = true"), 0, false);
        splitNode5.done();
        org.jbpm.ruleflow.core.factory.HumanTaskNodeFactory<?> humanTaskNode6 = factory.humanTaskNode(6);
        humanTaskNode6.name("Request proof of address");
        humanTaskNode6.workParameter("NodeName", "Request proof of address");
        humanTaskNode6.workParameter("ActorId", "amendments-ops");
        humanTaskNode6.done();
        humanTaskNode6.metaData("UniqueId", "c_evidence");
        humanTaskNode6.metaData("x", 810);
        humanTaskNode6.metaData("width", 140);
        humanTaskNode6.metaData("y", 80);
        humanTaskNode6.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.BoundaryEventNodeFactory<?> boundaryEventNode7 = factory.boundaryEventNode(7);
        boundaryEventNode7.name("Evidence SLA");
        boundaryEventNode7.eventType("Timer-c_evidence-PT72H-7");
        boundaryEventNode7.attachedTo("c_evidence");
        boundaryEventNode7.scope(null);
        boundaryEventNode7.metaData("UniqueId", "c_evidence_timer");
        boundaryEventNode7.metaData("EventType", "timer");
        boundaryEventNode7.metaData("x", 932);
        boundaryEventNode7.metaData("width", 36);
        boundaryEventNode7.metaData("y", 142);
        boundaryEventNode7.metaData("AttachedTo", "c_evidence");
        boundaryEventNode7.metaData("TimeDuration", "PT72H");
        boundaryEventNode7.metaData("CancelActivity", false);
        boundaryEventNode7.metaData("height", 36);
        boundaryEventNode7.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode8 = factory.workItemNode(8);
        workItemNode8.name("Send evidence reminder");
        workItemNode8.workName("EvidenceReminderTask");
        workItemNode8.workParameter("NodeName", "Send evidence reminder");
        workItemNode8.done();
        workItemNode8.metaData("UniqueId", "c_remind");
        workItemNode8.metaData("x", 60);
        workItemNode8.metaData("width", 140);
        workItemNode8.metaData("y", 205);
        workItemNode8.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode9 = factory.endNode(9);
        endNode9.name("End");
        endNode9.terminate(false);
        endNode9.metaData("UniqueId", "c_remind_end");
        endNode9.metaData("x", 270);
        endNode9.metaData("width", 36);
        endNode9.metaData("y", 161);
        endNode9.metaData("height", 36);
        endNode9.done();
        org.jbpm.ruleflow.core.factory.JoinFactory<?> joinNode10 = factory.joinNode(10);
        joinNode10.name("Join");
        joinNode10.type(2);
        joinNode10.metaData("UniqueId", "c_gw_join");
        joinNode10.metaData("x", 1020);
        joinNode10.metaData("width", 50);
        joinNode10.metaData("y", 80);
        joinNode10.metaData("height", 50);
        joinNode10.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode11 = factory.workItemNode(11);
        workItemNode11.name("Assess tax residency impact");
        workItemNode11.workName("TaxResidencyTask");
        workItemNode11.workParameter("NodeName", "Assess tax residency impact");
        workItemNode11.done();
        workItemNode11.metaData("UniqueId", "c_tax");
        workItemNode11.metaData("x", 1140);
        workItemNode11.metaData("width", 140);
        workItemNode11.metaData("y", 80);
        workItemNode11.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode12 = factory.workItemNode(12);
        workItemNode12.name("Apply address in core banking");
        workItemNode12.workName("CoreBankingTask");
        workItemNode12.workParameter("NodeName", "Apply address in core banking");
        workItemNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("item", "item", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_c_apply_item", "item", "java.lang.Object", null), null, null));
        workItemNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(), new org.jbpm.workflow.core.impl.DataDefinition("_di_c_apply_operation", "operation", "java.lang.Object", null), java.util.Arrays.asList(new org.jbpm.workflow.core.node.Assignment(null, new org.jbpm.workflow.core.impl.DataDefinition("c4ae16b1-e49d-4670-a67c-be749a27596d", "EXPRESSION (APPLY_ADDRESS)", "java.lang.Object", "APPLY_ADDRESS"), new org.jbpm.workflow.core.impl.DataDefinition("_di_c_apply_operation", "operation", "java.lang.Object", null))), null));
        workItemNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("accountId", "accountId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_c_apply_accountId", "accountId", "java.lang.Object", null), null, null));
        workItemNode12.mapDataInputAssociation(new org.jbpm.workflow.core.impl.DataAssociation(java.util.Arrays.asList(new org.jbpm.workflow.core.impl.DataDefinition("requestId", "requestId", "java.lang.Object", null)), new org.jbpm.workflow.core.impl.DataDefinition("_di_c_apply_idempotencyKey", "idempotencyKey", "java.lang.Object", null), null, null));
        workItemNode12.done();
        workItemNode12.metaData("UniqueId", "c_apply");
        workItemNode12.metaData("x", 1350);
        workItemNode12.metaData("width", 140);
        workItemNode12.metaData("y", 80);
        workItemNode12.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.BoundaryEventNodeFactory<?> boundaryEventNode13 = factory.boundaryEventNode(13);
        boundaryEventNode13.name("Core failure");
        boundaryEventNode13.eventType("Error-c_apply-CORE_BANKING_FAILURE");
        boundaryEventNode13.attachedTo("c_apply");
        boundaryEventNode13.scope(null);
        boundaryEventNode13.metaData("UniqueId", "c_apply_error");
        boundaryEventNode13.metaData("EventType", "error");
        boundaryEventNode13.metaData("ErrorStructureRef", null);
        boundaryEventNode13.metaData("ErrorEvent", "CORE_BANKING_FAILURE");
        boundaryEventNode13.metaData("x", 1472);
        boundaryEventNode13.metaData("width", 36);
        boundaryEventNode13.metaData("y", 142);
        boundaryEventNode13.metaData("AttachedTo", "c_apply");
        boundaryEventNode13.metaData("HasErrorEvent", true);
        boundaryEventNode13.metaData("height", 36);
        boundaryEventNode13.done();
        org.jbpm.ruleflow.core.factory.WorkItemNodeFactory<?> workItemNode14 = factory.workItemNode(14);
        workItemNode14.name("Record failure and refer");
        workItemNode14.workName("FailureRecordingTask");
        workItemNode14.workParameter("NodeName", "Record failure and refer");
        workItemNode14.done();
        workItemNode14.metaData("UniqueId", "c_compensate");
        workItemNode14.metaData("x", 60);
        workItemNode14.metaData("width", 140);
        workItemNode14.metaData("y", 80);
        workItemNode14.metaData("height", 80);
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode15 = factory.endNode(15);
        endNode15.name("Failed");
        endNode15.terminate(false);
        endNode15.metaData("UniqueId", "c_end_failed");
        endNode15.metaData("x", 270);
        endNode15.metaData("width", 36);
        endNode15.metaData("y", 80);
        endNode15.metaData("height", 36);
        endNode15.done();
        org.jbpm.ruleflow.core.factory.EndNodeFactory<?> endNode16 = factory.endNode(16);
        endNode16.name("Applied");
        endNode16.terminate(false);
        endNode16.metaData("UniqueId", "c_end");
        endNode16.metaData("x", 1560);
        endNode16.metaData("width", 36);
        endNode16.metaData("y", 80);
        endNode16.metaData("height", 36);
        endNode16.done();
        factory.errorExceptionHandler("Error-c_apply-CORE_BANKING_FAILURE", "CORE_BANKING_FAILURE", null);
        factory.connection(1, 2, "cf1");
        factory.connection(2, 3, "cf2");
        factory.connection(3, 4, "cf3");
        factory.connection(4, 5, "cf3b");
        factory.connection(5, 6, "cf_evidence");
        factory.connection(7, 8, "cf_timer");
        factory.connection(8, 9, "cf_remind_out");
        factory.connection(5, 10, "cf_ok");
        factory.connection(6, 10, "cf_evidence_out");
        factory.connection(10, 11, "cf4");
        factory.connection(11, 12, "cf5");
        factory.connection(13, 14, "cf_err");
        factory.connection(14, 15, "cf_err_out");
        factory.connection(12, 16, "cf6");
        factory.validate();
        return factory.getProcess();
    }
}
