package com.bank.amendments.model;

public enum EvidenceStatus {
    NOT_REQUIRED,
    OUTSTANDING,
    @com.fasterxml.jackson.annotation.JsonProperty("SUBMITTED")
    EVIDENCE_SUBMITTED,
    VERIFIED,
    REJECTED
}
