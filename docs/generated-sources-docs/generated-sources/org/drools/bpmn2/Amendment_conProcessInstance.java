package org.drools.bpmn2;

import org.drools.bpmn2.Amendment_conModel;

public class Amendment_conProcessInstance extends org.kie.kogito.process.impl.AbstractProcessInstance<Amendment_conModel> {

    public Amendment_conProcessInstance(org.drools.bpmn2.Amendment_conProcess process, Amendment_conModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, processRuntime);
    }

    public Amendment_conProcessInstance(org.drools.bpmn2.Amendment_conProcess process, Amendment_conModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, businessKey, processRuntime);
    }

    public Amendment_conProcessInstance(org.drools.bpmn2.Amendment_conProcess process, Amendment_conModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, processRuntime, wpi);
    }

    public Amendment_conProcessInstance(org.drools.bpmn2.Amendment_conProcess process, Amendment_conModel value, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, wpi);
    }

    public Amendment_conProcessInstance(org.drools.bpmn2.Amendment_conProcess process, Amendment_conModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.kogito.correlation.CompositeCorrelation correlation) {
        super(process, value, businessKey, processRuntime, correlation);
    }

    protected java.util.Map<String, Object> bind(Amendment_conModel variables) {
        if (null != variables)
            return variables.toMap();
        else
            return new java.util.HashMap();
    }

    protected void unbind(Amendment_conModel variables, java.util.Map<String, Object> vmap) {
        variables.fromMap(this.id(), vmap);
    }
}
