package com.bank.amendments.model;

import com.bank.amendments.model.*;
import java.time.Instant;

/** Outcome of the enterprise sanctions/PEP screening service. */
public class ScreeningResult implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


    private ScreeningOutcome outcome;
    private String referenceId;
    private Instant screenedAt;
    private String matchDetail;

    public ScreeningOutcome getOutcome() { return outcome; }
    public void setOutcome(ScreeningOutcome v) { this.outcome = v; }

    public String getReferenceId() { return referenceId; }
    public void setReferenceId(String v) { this.referenceId = v; }

    public Instant getScreenedAt() { return screenedAt; }
    public void setScreenedAt(Instant v) { this.screenedAt = v; }

    public String getMatchDetail() { return matchDetail; }
    public void setMatchDetail(String v) { this.matchDetail = v; }
}
