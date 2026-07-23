package com.bank.amendments.model;

public final class Enums {
    private Enums() {}

    public enum AmendmentType {
        COA,
        CON,
        JOINT_TO_SOLE
    }

    public enum Admissibility {
        PERMITTED,
        REVIEW_REQUIRED,
        REFUSED
    }

    public enum RequestStatus {
        SUBMITTED,
        SCREENING,
        ASSESSING,
        IN_PROGRESS,
        AWAITING_CUSTOMER,
        AWAITING_REVIEW,
        COMPLETED,
        PARTIALLY_COMPLETED,
        REFUSED,
        CANCELLED
    }

    public enum AmendmentStatus {
        PENDING,
        RUNNING,
        AWAITING_EVIDENCE,
        AWAITING_CONSENT,
        AWAITING_REVIEW,
        APPLIED,
        REFUSED,
        FAILED,
        CANCELLED
    }

    public enum Channel {
        DIGITAL,
        MOBILE,
        BRANCH,
        TELEPHONY
    }

    public enum AccountStatus {
        ACTIVE,
        DORMANT,
        FROZEN,
        BLOCKED,
        IN_CLOSURE,
        CLOSED
    }

    public enum PartyStatus {
        ACTIVE,
        DECEASED,
        INCAPACITATED,
        SUSPENDED
    }

    public enum ConsentStatus {
        NOT_REQUESTED,
        REQUESTED,
        GRANTED,
        DECLINED,
        EXPIRED
    }

    public enum ScreeningOutcome {
        CLEAR,
        POTENTIAL_MATCH,
        CONFIRMED_HIT,
        UNAVAILABLE
    }

    public enum RiskBand {
        LOW,
        MEDIUM,
        HIGH
    }

    public enum EvidenceStatus {
        NOT_REQUIRED,
        OUTSTANDING,
        SUBMITTED,
        VERIFIED,
        REJECTED
    }

    public enum ConNameChangeReason {
        MARRIAGE,
        CIVIL_PARTNERSHIP,
        DIVORCE,
        DEED_POLL,
        GENDER_RECOGNITION,
        SPELLING_CORRECTION
    }

    public enum ExecutionMode {
        PARALLEL,
        SEQUENTIAL
    }
}
