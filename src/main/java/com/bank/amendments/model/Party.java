package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;

/** An account party. Joint accounts carry two or more. */
public class Party {

    private String partyId;
    private String fullName;
    private PartyStatus status = PartyStatus.ACTIVE;
    private boolean requestor;
    private boolean remainingHolder;
    private ConsentStatus consentStatus = ConsentStatus.NOT_REQUESTED;
    private String mandateRole;
    private boolean powerOfAttorney;

    public String getPartyId() { return partyId; }
    public void setPartyId(String v) { this.partyId = v; }

    public String getFullName() { return fullName; }
    public void setFullName(String v) { this.fullName = v; }

    public PartyStatus getStatus() { return status; }
    public void setStatus(PartyStatus v) { this.status = v; }

    public boolean isRequestor() { return requestor; }
    public void setRequestor(boolean v) { this.requestor = v; }

    public boolean isRemainingHolder() { return remainingHolder; }
    public void setRemainingHolder(boolean v) { this.remainingHolder = v; }

    public ConsentStatus getConsentStatus() { return consentStatus; }
    public void setConsentStatus(ConsentStatus v) { this.consentStatus = v; }

    public String getMandateRole() { return mandateRole; }
    public void setMandateRole(String v) { this.mandateRole = v; }

    public boolean isPowerOfAttorney() { return powerOfAttorney; }
    public void setPowerOfAttorney(boolean v) { this.powerOfAttorney = v; }
}
