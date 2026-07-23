package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;

/** Change of address detail. */
public class CoaDetail {

    private Address currentAddress;
    private Address newAddress;
    private boolean correspondenceOnly;
    private EvidenceStatus evidenceStatus = EvidenceStatus.NOT_REQUIRED;
    private boolean addressVerified;
    private String verificationProvider;
    private String verificationReference;
    private boolean taxResidencyImpact;
    private boolean crsReassessmentRequired;

    public Address getCurrentAddress() { return currentAddress; }
    public void setCurrentAddress(Address v) { this.currentAddress = v; }

    public Address getNewAddress() { return newAddress; }
    public void setNewAddress(Address v) { this.newAddress = v; }

    public boolean isCorrespondenceOnly() { return correspondenceOnly; }
    public void setCorrespondenceOnly(boolean v) { this.correspondenceOnly = v; }

    public EvidenceStatus getEvidenceStatus() { return evidenceStatus; }
    public void setEvidenceStatus(EvidenceStatus v) { this.evidenceStatus = v; }

    public boolean isAddressVerified() { return addressVerified; }
    public void setAddressVerified(boolean v) { this.addressVerified = v; }

    public String getVerificationProvider() { return verificationProvider; }
    public void setVerificationProvider(String v) { this.verificationProvider = v; }

    public String getVerificationReference() { return verificationReference; }
    public void setVerificationReference(String v) { this.verificationReference = v; }

    public boolean isTaxResidencyImpact() { return taxResidencyImpact; }
    public void setTaxResidencyImpact(boolean v) { this.taxResidencyImpact = v; }

    public boolean isCrsReassessmentRequired() { return crsReassessmentRequired; }
    public void setCrsReassessmentRequired(boolean v) { this.crsReassessmentRequired = v; }
}
