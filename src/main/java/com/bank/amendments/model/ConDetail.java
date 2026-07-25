package com.bank.amendments.model;

import com.bank.amendments.model.*;

/** Change of name detail. */
public class ConDetail implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


    private String currentName;
    private String newName;
    private ConNameChangeReason reason;
    private String evidenceDocumentType;
    private EvidenceStatus evidenceStatus = EvidenceStatus.OUTSTANDING;
    private boolean rescreenRequired;
    private ScreeningResult rescreenResult;
    private boolean cardReissueRequired;

    public String getCurrentName() { return currentName; }
    public void setCurrentName(String v) { this.currentName = v; }

    public String getNewName() { return newName; }
    public void setNewName(String v) { this.newName = v; }

    public ConNameChangeReason getReason() { return reason; }
    public void setReason(ConNameChangeReason v) { this.reason = v; }

    public String getEvidenceDocumentType() { return evidenceDocumentType; }
    public void setEvidenceDocumentType(String v) { this.evidenceDocumentType = v; }

    public EvidenceStatus getEvidenceStatus() { return evidenceStatus; }
    public void setEvidenceStatus(EvidenceStatus v) { this.evidenceStatus = v; }

    public boolean isRescreenRequired() { return rescreenRequired; }
    public void setRescreenRequired(boolean v) { this.rescreenRequired = v; }

    public ScreeningResult getRescreenResult() { return rescreenResult; }
    public void setRescreenResult(ScreeningResult v) { this.rescreenResult = v; }

    public boolean isCardReissueRequired() { return cardReissueRequired; }
    public void setCardReissueRequired(boolean v) { this.cardReissueRequired = v; }
}
