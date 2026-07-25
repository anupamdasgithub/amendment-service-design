package com.bank.amendments.model;

import com.bank.amendments.model.*;
import java.util.ArrayList;
import java.util.List;

/** Point-in-time view of the account, read at intake. */
public class AccountSnapshot implements java.io.Serializable {
    private static final long serialVersionUID = 1L;


    private String accountId;
    private String productCode;
    private AccountStatus status;
    private boolean joint;
    private List<Party> parties = new ArrayList<>();
    private boolean overdraftFacility;
    private double overdraftBalance = 0.0;
    private boolean linkedProducts;
    private List<String> linkedProductCodes = new ArrayList<>();
    private RiskBand riskBand;
    private String jurisdiction;
    private boolean inFlightAmendment;

    public String getAccountId() { return accountId; }
    public void setAccountId(String v) { this.accountId = v; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String v) { this.productCode = v; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus v) { this.status = v; }

    public boolean isJoint() { return joint; }
    public void setJoint(boolean v) { this.joint = v; }

    public List<Party> getParties() { return parties; }
    public void setParties(List<Party> v) { this.parties = v; }

    public boolean isOverdraftFacility() { return overdraftFacility; }
    public void setOverdraftFacility(boolean v) { this.overdraftFacility = v; }

    public double getOverdraftBalance() { return overdraftBalance; }
    public void setOverdraftBalance(double v) { this.overdraftBalance = v; }

    public boolean isLinkedProducts() { return linkedProducts; }
    public void setLinkedProducts(boolean v) { this.linkedProducts = v; }

    public List<String> getLinkedProductCodes() { return linkedProductCodes; }
    public void setLinkedProductCodes(List<String> v) { this.linkedProductCodes = v; }

    public RiskBand getRiskBand() { return riskBand; }
    public void setRiskBand(RiskBand v) { this.riskBand = v; }

    public String getJurisdiction() { return jurisdiction; }
    public void setJurisdiction(String v) { this.jurisdiction = v; }

    public boolean isInFlightAmendment() { return inFlightAmendment; }
    public void setInFlightAmendment(boolean v) { this.inFlightAmendment = v; }
}
