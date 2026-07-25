package org.drools.bpmn2;

import org.drools.bpmn2.Amendment_joint_to_soleModel;

public class Amendment_joint_to_soleProcessInstance extends org.kie.kogito.process.impl.AbstractProcessInstance<Amendment_joint_to_soleModel> {

    public Amendment_joint_to_soleProcessInstance(org.drools.bpmn2.Amendment_joint_to_soleProcess process, Amendment_joint_to_soleModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, processRuntime);
    }

    public Amendment_joint_to_soleProcessInstance(org.drools.bpmn2.Amendment_joint_to_soleProcess process, Amendment_joint_to_soleModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, businessKey, processRuntime);
    }

    public Amendment_joint_to_soleProcessInstance(org.drools.bpmn2.Amendment_joint_to_soleProcess process, Amendment_joint_to_soleModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, processRuntime, wpi);
    }

    public Amendment_joint_to_soleProcessInstance(org.drools.bpmn2.Amendment_joint_to_soleProcess process, Amendment_joint_to_soleModel value, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, wpi);
    }

    public Amendment_joint_to_soleProcessInstance(org.drools.bpmn2.Amendment_joint_to_soleProcess process, Amendment_joint_to_soleModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.kogito.correlation.CompositeCorrelation correlation) {
        super(process, value, businessKey, processRuntime, correlation);
    }

    protected java.util.Map<String, Object> bind(Amendment_joint_to_soleModel variables) {
        if (null != variables)
            return variables.toMap();
        else
            return new java.util.HashMap();
    }

    protected void unbind(Amendment_joint_to_soleModel variables, java.util.Map<String, Object> vmap) {
        variables.fromMap(this.id(), vmap);
    }
}
