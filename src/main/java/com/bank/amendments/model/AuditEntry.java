package com.bank.amendments.model;

import java.time.Instant;

/** Append-only audit record. Emitted to Kafka and persisted. */
public class AuditEntry {

    private Instant at;
    private String actor;
    private String action;
    private String detail;
    private String itemId;

    public AuditEntry() {}

    public AuditEntry(Instant at, String actor, String action, String detail) {
        this.at = at;
        this.actor = actor;
        this.action = action;
        this.detail = detail;
    }

    public Instant getAt() { return at; }
    public void setAt(Instant v) { this.at = v; }

    public String getActor() { return actor; }
    public void setActor(String v) { this.actor = v; }

    public String getAction() { return action; }
    public void setAction(String v) { this.action = v; }

    public String getDetail() { return detail; }
    public void setDetail(String v) { this.detail = v; }

    public String getItemId() { return itemId; }
    public void setItemId(String v) { this.itemId = v; }
}
