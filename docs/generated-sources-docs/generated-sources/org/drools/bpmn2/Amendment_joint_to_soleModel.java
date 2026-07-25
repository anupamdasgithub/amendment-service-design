/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

@org.kie.kogito.codegen.Generated(value = "kogito-codegen", reference = "amendment_joint_to_sole", name = "Amendment_joint_to_sole", hidden = false)
public class Amendment_joint_to_soleModel implements org.kie.kogito.Model, MapInput, MapInputId, MapOutput, MappableToModel<Amendment_joint_to_soleModelOutput> {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
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

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "jtsEligible")
    private java.lang.Object jtsEligible;

    public java.lang.Object getJtsEligible() {
        return jtsEligible;
    }

    public void setJtsEligible(java.lang.Object jtsEligible) {
        this.jtsEligible = jtsEligible;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "JtsEligibility")
    private java.lang.Object JtsEligibility;

    public java.lang.Object getJtsEligibility() {
        return JtsEligibility;
    }

    public void setJtsEligibility(java.lang.Object JtsEligibility) {
        this.JtsEligibility = JtsEligibility;
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
    @com.fasterxml.jackson.annotation.JsonProperty(value = "jtsDiverted")
    private java.lang.Object jtsDiverted;

    public java.lang.Object getJtsDiverted() {
        return jtsDiverted;
    }

    public void setJtsDiverted(java.lang.Object jtsDiverted) {
        this.jtsDiverted = jtsDiverted;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "allConsentsGranted")
    private java.lang.Object allConsentsGranted;

    public java.lang.Object getAllConsentsGranted() {
        return allConsentsGranted;
    }

    public void setAllConsentsGranted(java.lang.Object allConsentsGranted) {
        this.allConsentsGranted = allConsentsGranted;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "jtsReviewRequired")
    private java.lang.Object jtsReviewRequired;

    public java.lang.Object getJtsReviewRequired() {
        return jtsReviewRequired;
    }

    public void setJtsReviewRequired(java.lang.Object jtsReviewRequired) {
        this.jtsReviewRequired = jtsReviewRequired;
    }

    @Override()
    public Amendment_joint_to_soleModelOutput toModel() {
        Amendment_joint_to_soleModelOutput result = new Amendment_joint_to_soleModelOutput();
        result.setId(getId());
        result.setAccountId(getAccountId());
        result.setJtsEligible(getJtsEligible());
        result.setJtsEligibility(getJtsEligibility());
        result.setJtsDiverted(getJtsDiverted());
        result.setAllConsentsGranted(getAllConsentsGranted());
        result.setJtsReviewRequired(getJtsReviewRequired());
        return result;
    }
}
