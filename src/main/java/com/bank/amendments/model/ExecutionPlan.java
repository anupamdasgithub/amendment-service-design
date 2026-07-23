package com.bank.amendments.model;

import com.bank.amendments.model.Enums.*;
import java.util.ArrayList;
import java.util.List;

/** Output of the AmendmentSequencing decision. */
public class ExecutionPlan {

    private ExecutionMode mode;
    private List<String> parallelGroup = new ArrayList<>();
    private List<String> orderedSequence = new ArrayList<>();
    private String rationale;

    public ExecutionMode getMode() { return mode; }
    public void setMode(ExecutionMode v) { this.mode = v; }

    public List<String> getParallelGroup() { return parallelGroup; }
    public void setParallelGroup(List<String> v) { this.parallelGroup = v; }

    public List<String> getOrderedSequence() { return orderedSequence; }
    public void setOrderedSequence(List<String> v) { this.orderedSequence = v; }

    public String getRationale() { return rationale; }
    public void setRationale(String v) { this.rationale = v; }
}
