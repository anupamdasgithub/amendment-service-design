package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;
import java.time.Instant;

/**
 * One requested amendment within a request. One item maps to one child
 * process instance when admissible.
 */
public class AmendmentItem {

    private String itemId;
    private AmendmentType type;
    private AmendmentStatus status = AmendmentStatus.PENDING;
    private Admissibility admissibility;
    private String reasonCode;
    private String reasonText;
    private Integer sequenceOrder;
    private String childProcessInstanceId;
    private Instant startedAt;
    private Instant completedAt;

    private CoaDetail coa;
    private ConDetail con;
    private JointToSoleDetail jointToSole;

    public String getItemId() { return itemId; }
    public void setItemId(String v) { this.itemId = v; }

    public AmendmentType getType() { return type; }
    public void setType(AmendmentType v) { this.type = v; }

    public AmendmentStatus getStatus() { return status; }
    public void setStatus(AmendmentStatus v) { this.status = v; }

    public Admissibility getAdmissibility() { return admissibility; }
    public void setAdmissibility(Admissibility v) { this.admissibility = v; }

    public String getReasonCode() { return reasonCode; }
    public void setReasonCode(String v) { this.reasonCode = v; }

    public String getReasonText() { return reasonText; }
    public void setReasonText(String v) { this.reasonText = v; }

    public Integer getSequenceOrder() { return sequenceOrder; }
    public void setSequenceOrder(Integer v) { this.sequenceOrder = v; }

    public String getChildProcessInstanceId() { return childProcessInstanceId; }
    public void setChildProcessInstanceId(String v) { this.childProcessInstanceId = v; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant v) { this.startedAt = v; }

    public Instant getCompletedAt() { return completedAt; }
    public void setCompletedAt(Instant v) { this.completedAt = v; }

    public CoaDetail getCoa() { return coa; }
    public void setCoa(CoaDetail v) { this.coa = v; }

    public ConDetail getCon() { return con; }
    public void setCon(ConDetail v) { this.con = v; }

    public JointToSoleDetail getJointToSole() { return jointToSole; }
    public void setJointToSole(JointToSoleDetail v) { this.jointToSole = v; }
}
