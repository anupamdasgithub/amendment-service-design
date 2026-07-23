package com.bank.amendments.handler;

import java.time.Instant;

public class CoreBankingResponse {

    private boolean success;
    private String reference;
    private Instant appliedAt;
    private String failureCode;
    private String failureText;

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean v) { this.success = v; }

    public String getReference() { return reference; }
    public void setReference(String v) { this.reference = v; }

    public Instant getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Instant v) { this.appliedAt = v; }

    public String getFailureCode() { return failureCode; }
    public void setFailureCode(String v) { this.failureCode = v; }

    public String getFailureText() { return failureText; }
    public void setFailureText(String v) { this.failureText = v; }
}
