package com.bank.amendments.model;

public enum AmendmentStatus {
    PENDING,
    RUNNING,
    AWAITING_EVIDENCE,
    AWAITING_CONSENT,
    @com.fasterxml.jackson.annotation.JsonProperty("AWAITING_REVIEW")
    AMENDMENT_AWAITING_REVIEW,
    APPLIED,
    @com.fasterxml.jackson.annotation.JsonProperty("REFUSED")
    AMENDMENT_REFUSED,
    FAILED,
    @com.fasterxml.jackson.annotation.JsonProperty("CANCELLED")
    AMENDMENT_CANCELLED
}
