package com.bank.amendments.model;

import com.bank.amendments.model.*;
import java.time.Instant;

/** Reference to a document held in the enterprise DMS. */
public class DocumentRef implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


    private String documentId;
    private String dmsUri;
    private String documentType;
    private EvidenceStatus status = EvidenceStatus.OUTSTANDING;
    private String relatedItemId;
    private Instant uploadedAt;
    private String rejectionReason;

    public String getDocumentId() { return documentId; }
    public void setDocumentId(String v) { this.documentId = v; }

    public String getDmsUri() { return dmsUri; }
    public void setDmsUri(String v) { this.dmsUri = v; }

    public String getDocumentType() { return documentType; }
    public void setDocumentType(String v) { this.documentType = v; }

    public EvidenceStatus getStatus() { return status; }
    public void setStatus(EvidenceStatus v) { this.status = v; }

    public String getRelatedItemId() { return relatedItemId; }
    public void setRelatedItemId(String v) { this.relatedItemId = v; }

    public Instant getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(Instant v) { this.uploadedAt = v; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String v) { this.rejectionReason = v; }
}
