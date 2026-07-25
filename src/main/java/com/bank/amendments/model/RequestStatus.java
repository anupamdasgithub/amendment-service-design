package com.bank.amendments.model;

public enum RequestStatus {
    SUBMITTED,
    SCREENING,
    ASSESSING,
    IN_PROGRESS,
    AWAITING_CUSTOMER,
    AWAITING_REVIEW,
    COMPLETED,
    PARTIALLY_COMPLETED,
    @com.fasterxml.jackson.annotation.JsonProperty("REFUSED")
    REQUEST_REFUSED,
    CANCELLED
}
