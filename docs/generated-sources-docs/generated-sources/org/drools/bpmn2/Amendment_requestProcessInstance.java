package org.drools.bpmn2;

import org.drools.bpmn2.Amendment_requestModel;

public class Amendment_requestProcessInstance extends org.kie.kogito.process.impl.AbstractProcessInstance<Amendment_requestModel> {

    public Amendment_requestProcessInstance(org.drools.bpmn2.Amendment_requestProcess process, Amendment_requestModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, processRuntime);
    }

    public Amendment_requestProcessInstance(org.drools.bpmn2.Amendment_requestProcess process, Amendment_requestModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime) {
        super(process, value, businessKey, processRuntime);
    }

    public Amendment_requestProcessInstance(org.drools.bpmn2.Amendment_requestProcess process, Amendment_requestModel value, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, processRuntime, wpi);
    }

    public Amendment_requestProcessInstance(org.drools.bpmn2.Amendment_requestProcess process, Amendment_requestModel value, org.kie.api.runtime.process.WorkflowProcessInstance wpi) {
        super(process, value, wpi);
    }

    public Amendment_requestProcessInstance(org.drools.bpmn2.Amendment_requestProcess process, Amendment_requestModel value, java.lang.String businessKey, org.kie.api.runtime.process.ProcessRuntime processRuntime, org.kie.kogito.correlation.CompositeCorrelation correlation) {
        super(process, value, businessKey, processRuntime, correlation);
    }

    protected java.util.Map<String, Object> bind(Amendment_requestModel variables) {
        if (null != variables)
            return variables.toMap();
        else
            return new java.util.HashMap();
    }

    protected void unbind(Amendment_requestModel variables, java.util.Map<String, Object> vmap) {
        variables.fromMap(this.id(), vmap);
    }
}
