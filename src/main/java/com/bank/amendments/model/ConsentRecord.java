package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;
import java.time.Instant;

/** Consent from one departing party on a joint to sole conversion. */
public class ConsentRecord {

    private String partyId;
    private ConsentStatus status = ConsentStatus.NOT_REQUESTED;
    private Instant requestedAt;
    private Instant respondedAt;
    private Instant expiresAt;
    private String channel;
    private String declineReason;
    private String evidenceRef;

    public String getPartyId() { return partyId; }
    public void setPartyId(String v) { this.partyId = v; }

    public ConsentStatus getStatus() { return status; }
    public void setStatus(ConsentStatus v) { this.status = v; }

    public Instant getRequestedAt() { return requestedAt; }
    public void setRequestedAt(Instant v) { this.requestedAt = v; }

    public Instant getRespondedAt() { return respondedAt; }
    public void setRespondedAt(Instant v) { this.respondedAt = v; }

    public Instant getExpiresAt() { return expiresAt; }
    public void setExpiresAt(Instant v) { this.expiresAt = v; }

    public String getChannel() { return channel; }
    public void setChannel(String v) { this.channel = v; }

    public String getDeclineReason() { return declineReason; }
    public void setDeclineReason(String v) { this.declineReason = v; }

    public String getEvidenceRef() { return evidenceRef; }
    public void setEvidenceRef(String v) { this.evidenceRef = v; }
}
