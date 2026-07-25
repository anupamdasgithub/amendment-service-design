package com.bank.amendments.model;

public enum PartyStatus {
    @com.fasterxml.jackson.annotation.JsonProperty("ACTIVE")
    PARTY_ACTIVE,
    DECEASED,
    INCAPACITATED,
    SUSPENDED
}
