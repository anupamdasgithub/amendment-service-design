package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Case file for a customer-initiated amendment request.
 * Carried as the root process variable of the parent process.
 */
public class AmendmentRequest {

    private String requestId;
    private String customerId;
    private String accountId;
    private String requestorPartyId;
    private Channel channel;
    private Instant submittedAt;
    private RequestStatus status = RequestStatus.SUBMITTED;

    private List<AmendmentType> requestedTypes = new ArrayList<>();
    private List<AmendmentItem> items = new ArrayList<>();
    private List<DocumentRef> documents = new ArrayList<>();
    private List<AuditEntry> audit = new ArrayList<>();

    private AccountSnapshot account;
    private ScreeningResult screening;
    private ExecutionPlan executionPlan;

    private String cancellationReason;
    private Instant slaDueAt;

    public String getRequestId() { return requestId; }
    public void setRequestId(String v) { this.requestId = v; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String v) { this.customerId = v; }

    public String getAccountId() { return accountId; }
    public void setAccountId(String v) { this.accountId = v; }

    public String getRequestorPartyId() { return requestorPartyId; }
    public void setRequestorPartyId(String v) { this.requestorPartyId = v; }

    public Channel getChannel() { return channel; }
    public void setChannel(Channel v) { this.channel = v; }

    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant v) { this.submittedAt = v; }

    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus v) { this.status = v; }

    public List<AmendmentType> getRequestedTypes() { return requestedTypes; }
    public void setRequestedTypes(List<AmendmentType> v) { this.requestedTypes = v; }

    public List<AmendmentItem> getItems() { return items; }
    public void setItems(List<AmendmentItem> v) { this.items = v; }

    public List<DocumentRef> getDocuments() { return documents; }
    public void setDocuments(List<DocumentRef> v) { this.documents = v; }

    public List<AuditEntry> getAudit() { return audit; }
    public void setAudit(List<AuditEntry> v) { this.audit = v; }

    public AccountSnapshot getAccount() { return account; }
    public void setAccount(AccountSnapshot v) { this.account = v; }

    public ScreeningResult getScreening() { return screening; }
    public void setScreening(ScreeningResult v) { this.screening = v; }

    public ExecutionPlan getExecutionPlan() { return executionPlan; }
    public void setExecutionPlan(ExecutionPlan v) { this.executionPlan = v; }

    public String getCancellationReason() { return cancellationReason; }
    public void setCancellationReason(String v) { this.cancellationReason = v; }

    public Instant getSlaDueAt() { return slaDueAt; }
    public void setSlaDueAt(Instant v) { this.slaDueAt = v; }
}
