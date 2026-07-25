/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.bpmn2;

import org.kie.kogito.MapInput;
import org.kie.kogito.MapInputId;
import org.kie.kogito.MapOutput;
import java.util.Map;
import java.util.HashMap;
import org.kie.kogito.MappableToModel;
import org.kie.kogito.Model;
import org.kie.kogito.ProcessInput;

@org.kie.kogito.codegen.Generated(value = "kogito-codegen", reference = "amendment_con", name = "Amendment_con", hidden = true)
@ProcessInput(processName = "amendment_con")
public class Amendment_conModelInput implements Model, MapInput, MapInputId, MapOutput, MappableToModel<Amendment_conModel> {

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "rescreenClear")
    private java.lang.Object rescreenClear;

    public java.lang.Object getRescreenClear() {
        return rescreenClear;
    }

    public void setRescreenClear(java.lang.Object rescreenClear) {
        this.rescreenClear = rescreenClear;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "accountId")
    private java.lang.Object accountId;

    public java.lang.Object getAccountId() {
        return accountId;
    }

    public void setAccountId(java.lang.Object accountId) {
        this.accountId = accountId;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "item")
    private com.bank.amendments.model.AmendmentItem item;

    public com.bank.amendments.model.AmendmentItem getItem() {
        return item;
    }

    public void setItem(com.bank.amendments.model.AmendmentItem item) {
        this.item = item;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "requestId")
    private java.lang.String requestId;

    public java.lang.String getRequestId() {
        return requestId;
    }

    public void setRequestId(java.lang.String requestId) {
        this.requestId = requestId;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "rescreenOutcome")
    private java.lang.Object rescreenOutcome;

    public java.lang.Object getRescreenOutcome() {
        return rescreenOutcome;
    }

    public void setRescreenOutcome(java.lang.Object rescreenOutcome) {
        this.rescreenOutcome = rescreenOutcome;
    }

    @Override()
    public Amendment_conModel toModel() {
        Amendment_conModel result = new Amendment_conModel();
        result.setRescreenClear(getRescreenClear());
        result.setAccountId(getAccountId());
        result.setItem(getItem());
        result.setRequestId(getRequestId());
        result.setRescreenOutcome(getRescreenOutcome());
        return result;
    }
}
