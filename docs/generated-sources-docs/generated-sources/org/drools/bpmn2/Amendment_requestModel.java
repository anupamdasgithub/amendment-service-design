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

@org.kie.kogito.codegen.Generated(value = "kogito-codegen", reference = "amendment_request", name = "Amendment_request", hidden = false)
public class Amendment_requestModel implements org.kie.kogito.Model, MapInput, MapInputId, MapOutput, MappableToModel<Amendment_requestModelOutput> {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "request")
    private com.bank.amendments.model.AmendmentRequest request;

    public com.bank.amendments.model.AmendmentRequest getRequest() {
        return request;
    }

    public void setRequest(com.bank.amendments.model.AmendmentRequest request) {
        this.request = request;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "amendmentItems")
    private java.lang.Object amendmentItems;

    public java.lang.Object getAmendmentItems() {
        return amendmentItems;
    }

    public void setAmendmentItems(java.lang.Object amendmentItems) {
        this.amendmentItems = amendmentItems;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "itemType")
    private java.lang.String itemType;

    public java.lang.String getItemType() {
        return itemType;
    }

    public void setItemType(java.lang.String itemType) {
        this.itemType = itemType;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "includesCon")
    private java.lang.Boolean includesCon;

    public java.lang.Boolean getIncludesCon() {
        return includesCon;
    }

    public void setIncludesCon(java.lang.Boolean includesCon) {
        this.includesCon = includesCon;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "isCon")
    private java.lang.Boolean isCon;

    public java.lang.Boolean getIsCon() {
        return isCon;
    }

    public void setIsCon(java.lang.Boolean isCon) {
        this.isCon = isCon;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "anyPermitted")
    private java.lang.Boolean anyPermitted;

    public java.lang.Boolean getAnyPermitted() {
        return anyPermitted;
    }

    public void setAnyPermitted(java.lang.Boolean anyPermitted) {
        this.anyPermitted = anyPermitted;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "channel")
    private java.lang.String channel;

    public java.lang.String getChannel() {
        return channel;
    }

    public void setChannel(java.lang.String channel) {
        this.channel = channel;
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

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "accountStatus")
    private java.lang.String accountStatus;

    public java.lang.String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(java.lang.String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "riskBand")
    private java.lang.String riskBand;

    public java.lang.String getRiskBand() {
        return riskBand;
    }

    public void setRiskBand(java.lang.String riskBand) {
        this.riskBand = riskBand;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "nameChangeReason")
    private java.lang.Object nameChangeReason;

    public java.lang.Object getNameChangeReason() {
        return nameChangeReason;
    }

    public void setNameChangeReason(java.lang.Object nameChangeReason) {
        this.nameChangeReason = nameChangeReason;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "output")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "ExecutionPlan")
    private java.lang.Object ExecutionPlan;

    public java.lang.Object getExecutionPlan() {
        return ExecutionPlan;
    }

    public void setExecutionPlan(java.lang.Object ExecutionPlan) {
        this.ExecutionPlan = ExecutionPlan;
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

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "inFlightAmendment")
    private java.lang.Boolean inFlightAmendment;

    public java.lang.Boolean getInFlightAmendment() {
        return inFlightAmendment;
    }

    public void setInFlightAmendment(java.lang.Boolean inFlightAmendment) {
        this.inFlightAmendment = inFlightAmendment;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "isCoa")
    private java.lang.Boolean isCoa;

    public java.lang.Boolean getIsCoa() {
        return isCoa;
    }

    public void setIsCoa(java.lang.Boolean isCoa) {
        this.isCoa = isCoa;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "requestorIsParty")
    private java.lang.Boolean requestorIsParty;

    public java.lang.Boolean getRequestorIsParty() {
        return requestorIsParty;
    }

    public void setRequestorIsParty(java.lang.Boolean requestorIsParty) {
        this.requestorIsParty = requestorIsParty;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "remainingPartyEligible")
    private java.lang.Object remainingPartyEligible;

    public java.lang.Object getRemainingPartyEligible() {
        return remainingPartyEligible;
    }

    public void setRemainingPartyEligible(java.lang.Object remainingPartyEligible) {
        this.remainingPartyEligible = remainingPartyEligible;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "addressVerified")
    private java.lang.Object addressVerified;

    public java.lang.Object getAddressVerified() {
        return addressVerified;
    }

    public void setAddressVerified(java.lang.Object addressVerified) {
        this.addressVerified = addressVerified;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "includesCoa")
    private java.lang.Boolean includesCoa;

    public java.lang.Boolean getIncludesCoa() {
        return includesCoa;
    }

    public void setIncludesCoa(java.lang.Boolean includesCoa) {
        this.includesCoa = includesCoa;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "mandatePermits")
    private java.lang.Boolean mandatePermits;

    public java.lang.Boolean getMandatePermits() {
        return mandatePermits;
    }

    public void setMandatePermits(java.lang.Boolean mandatePermits) {
        this.mandatePermits = mandatePermits;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "output")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "admissibility")
    private java.lang.Object admissibility;

    public java.lang.Object getAdmissibility() {
        return admissibility;
    }

    public void setAdmissibility(java.lang.Object admissibility) {
        this.admissibility = admissibility;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "amendmentType")
    private java.lang.String amendmentType;

    public java.lang.String getAmendmentType() {
        return amendmentType;
    }

    public void setAmendmentType(java.lang.String amendmentType) {
        this.amendmentType = amendmentType;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "includesJointToSole")
    private java.lang.Boolean includesJointToSole;

    public java.lang.Boolean getIncludesJointToSole() {
        return includesJointToSole;
    }

    public void setIncludesJointToSole(java.lang.Boolean includesJointToSole) {
        this.includesJointToSole = includesJointToSole;
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

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "jointLiabilities")
    private java.lang.Object jointLiabilities;

    public java.lang.Object getJointLiabilities() {
        return jointLiabilities;
    }

    public void setJointLiabilities(java.lang.Object jointLiabilities) {
        this.jointLiabilities = jointLiabilities;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "screeningOutcome")
    private java.lang.String screeningOutcome;

    public java.lang.String getScreeningOutcome() {
        return screeningOutcome;
    }

    public void setScreeningOutcome(java.lang.String screeningOutcome) {
        this.screeningOutcome = screeningOutcome;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "permittedCount")
    private java.lang.Integer permittedCount;

    public java.lang.Integer getPermittedCount() {
        return permittedCount;
    }

    public void setPermittedCount(java.lang.Integer permittedCount) {
        this.permittedCount = permittedCount;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "otherPartyStatus")
    private java.lang.Object otherPartyStatus;

    public java.lang.Object getOtherPartyStatus() {
        return otherPartyStatus;
    }

    public void setOtherPartyStatus(java.lang.Object otherPartyStatus) {
        this.otherPartyStatus = otherPartyStatus;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "input")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "accountIsJoint")
    private java.lang.Boolean accountIsJoint;

    public java.lang.Boolean getAccountIsJoint() {
        return accountIsJoint;
    }

    public void setAccountIsJoint(java.lang.Boolean accountIsJoint) {
        this.accountIsJoint = accountIsJoint;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "isJointToSole")
    private java.lang.Boolean isJointToSole;

    public java.lang.Boolean getIsJointToSole() {
        return isJointToSole;
    }

    public void setIsJointToSole(java.lang.Boolean isJointToSole) {
        this.isJointToSole = isJointToSole;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "accountId")
    private java.lang.String accountId;

    public java.lang.String getAccountId() {
        return accountId;
    }

    public void setAccountId(java.lang.String accountId) {
        this.accountId = accountId;
    }

    @org.kie.kogito.codegen.VariableInfo(tags = "")
    @com.fasterxml.jackson.annotation.JsonProperty(value = "cancelled")
    private java.lang.Boolean cancelled;

    public java.lang.Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(java.lang.Boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override()
    public Amendment_requestModelOutput toModel() {
        Amendment_requestModelOutput result = new Amendment_requestModelOutput();
        result.setId(getId());
        result.setAdmissibility(getAdmissibility());
        result.setAmendmentItems(getAmendmentItems());
        result.setItemType(getItemType());
        result.setIsCon(getIsCon());
        result.setAnyPermitted(getAnyPermitted());
        result.setRescreenOutcome(getRescreenOutcome());
        result.setJointLiabilities(getJointLiabilities());
        result.setAllConsentsGranted(getAllConsentsGranted());
        result.setOtherPartyStatus(getOtherPartyStatus());
        result.setIsJointToSole(getIsJointToSole());
        result.setAccountId(getAccountId());
        result.setNameChangeReason(getNameChangeReason());
        result.setExecutionPlan(getExecutionPlan());
        result.setIsCoa(getIsCoa());
        result.setCancelled(getCancelled());
        result.setRemainingPartyEligible(getRemainingPartyEligible());
        result.setAddressVerified(getAddressVerified());
        return result;
    }
}
