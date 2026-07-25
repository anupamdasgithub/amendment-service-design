package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Joint to sole conversion detail. Carries the most eligibility surface
 * of the three amendment types.
 */
public class JointToSoleDetail {

    private String remainingPartyId;
    private List<String> departingPartyIds = new ArrayList<>();
    private List<ConsentRecord> consents = new ArrayList<>();
    private boolean allConsentsGranted;
    private boolean anyPartyDeceased;
    private boolean anyPartyIncapacitated;
    private boolean jointLiabilities;
    private BigDecimal outstandingBalance = BigDecimal.ZERO;
    private boolean linkedProducts;
    private boolean remainingPartyEligible;
    private String eligibilityAssessmentRef;
    private boolean requiresUnderwriting;
    private String divertedToJourney;

    public String getRemainingPartyId() { return remainingPartyId; }
    public void setRemainingPartyId(String v) { this.remainingPartyId = v; }

    public List<String> getDepartingPartyIds() { return departingPartyIds; }
    public void setDepartingPartyIds(List<String> v) { this.departingPartyIds = v; }

    public List<ConsentRecord> getConsents() { return consents; }
    public void setConsents(List<ConsentRecord> v) { this.consents = v; }

    public boolean isAllConsentsGranted() { return allConsentsGranted; }
    public void setAllConsentsGranted(boolean v) { this.allConsentsGranted = v; }

    public boolean isAnyPartyDeceased() { return anyPartyDeceased; }
    public void setAnyPartyDeceased(boolean v) { this.anyPartyDeceased = v; }

    public boolean isAnyPartyIncapacitated() { return anyPartyIncapacitated; }
    public void setAnyPartyIncapacitated(boolean v) { this.anyPartyIncapacitated = v; }

    public boolean isJointLiabilities() { return jointLiabilities; }
    public void setJointLiabilities(boolean v) { this.jointLiabilities = v; }

    public BigDecimal getOutstandingBalance() { return outstandingBalance; }
    public void setOutstandingBalance(BigDecimal v) { this.outstandingBalance = v; }

    public boolean isLinkedProducts() { return linkedProducts; }
    public void setLinkedProducts(boolean v) { this.linkedProducts = v; }

    public boolean isRemainingPartyEligible() { return remainingPartyEligible; }
    public void setRemainingPartyEligible(boolean v) { this.remainingPartyEligible = v; }

    public String getEligibilityAssessmentRef() { return eligibilityAssessmentRef; }
    public void setEligibilityAssessmentRef(String v) { this.eligibilityAssessmentRef = v; }

    public boolean isRequiresUnderwriting() { return requiresUnderwriting; }
    public void setRequiresUnderwriting(boolean v) { this.requiresUnderwriting = v; }

    public String getDivertedToJourney() { return divertedToJourney; }
    public void setDivertedToJourney(String v) { this.divertedToJourney = v; }
}
