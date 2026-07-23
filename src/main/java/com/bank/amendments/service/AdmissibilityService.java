package com.bank.amendments.service;

import com.bank.amendments.model.*;
import com.bank.amendments.model.Enums.*;
import org.kie.kogito.decision.DecisionModel;
import org.kie.kogito.decision.DecisionModels;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Evaluates the parent admissibility decision per requested amendment type.
 *
 * Admissibility is deliberately cheap and uses only data available at
 * intake. Detailed type-specific eligibility runs inside each child
 * process where the required data has been gathered.
 */
@Service
public class AdmissibilityService {

    private static final String NAMESPACE = "https://bank.com/dmn/amendments";
    private static final String ADMISSIBILITY_MODEL = "AmendmentAdmissibility";
    private static final String SEQUENCING_MODEL = "AmendmentSequencing";

    private final DecisionModels decisionModels;

    public AdmissibilityService(DecisionModels decisionModels) {
        this.decisionModels = decisionModels;
    }

    public void assess(AmendmentRequest request) {
        DecisionModel model = decisionModels.getDecisionModel(NAMESPACE, ADMISSIBILITY_MODEL);

        for (AmendmentItem item : request.getItems()) {
            DMNContext context = model.newContext(buildInputs(request, item));
            DMNResult result = model.evaluateAll(context);

            @SuppressWarnings("unchecked")
            Map<String, Object> outcome =
                    (Map<String, Object>) result.getDecisionResultByName("Admissibility").getResult();

            item.setAdmissibility(Admissibility.valueOf((String) outcome.get("admissibility")));
            item.setReasonCode((String) outcome.get("reasonCode"));
            item.setReasonText((String) outcome.get("reasonText"));

            if (item.getAdmissibility() == Admissibility.REFUSED) {
                item.setStatus(AmendmentStatus.REFUSED);
            }
        }
    }

    public void plan(AmendmentRequest request) {
        List<AmendmentItem> permitted = request.getItems().stream()
                .filter(i -> i.getAdmissibility() != Admissibility.REFUSED)
                .toList();

        DecisionModel model = decisionModels.getDecisionModel(NAMESPACE, SEQUENCING_MODEL);

        Map<String, Object> inputs = new HashMap<>();
        inputs.put("PermittedCount", permitted.size());
        inputs.put("IncludesJointToSole", contains(permitted, AmendmentType.JOINT_TO_SOLE));
        inputs.put("IncludesCon", contains(permitted, AmendmentType.CON));
        inputs.put("IncludesCoa", contains(permitted, AmendmentType.COA));

        DMNResult result = model.evaluateAll(model.newContext(inputs));

        @SuppressWarnings("unchecked")
        Map<String, Object> planOut =
                (Map<String, Object>) result.getDecisionResultByName("ExecutionPlan").getResult();

        ExecutionPlan plan = new ExecutionPlan();
        plan.setMode(ExecutionMode.valueOf((String) planOut.get("mode")));
        plan.setRationale((String) planOut.get("rationale"));

        String ordered = (String) planOut.get("orderedSequence");
        if (ordered != null && !ordered.isBlank()) {
            plan.setOrderedSequence(new ArrayList<>(List.of(ordered.split(","))));
            applyOrder(permitted, plan.getOrderedSequence());
        } else {
            plan.setParallelGroup(permitted.stream().map(i -> i.getType().name()).toList());
        }

        request.setExecutionPlan(plan);
    }

    private Map<String, Object> buildInputs(AmendmentRequest request, AmendmentItem item) {
        AccountSnapshot account = request.getAccount();
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("AmendmentType", item.getType().name());
        inputs.put("AccountStatus", account.getStatus().name());
        inputs.put("RequestorIsParty", isRequestorAParty(request));
        inputs.put("MandatePermits", mandatePermits(request));
        inputs.put("Channel", request.getChannel().name());
        inputs.put("ScreeningOutcome", request.getScreening().getOutcome().name());
        inputs.put("InFlightAmendment", account.isInFlightAmendment());
        inputs.put("AccountIsJoint", account.isJoint());
        inputs.put("RiskBand", account.getRiskBand().name());
        return inputs;
    }

    private boolean isRequestorAParty(AmendmentRequest request) {
        return request.getAccount().getParties().stream()
                .anyMatch(p -> p.getPartyId().equals(request.getRequestorPartyId()));
    }

    private boolean mandatePermits(AmendmentRequest request) {
        return request.getAccount().getParties().stream()
                .filter(p -> p.getPartyId().equals(request.getRequestorPartyId()))
                .findFirst()
                .map(p -> !"VIEW_ONLY".equals(p.getMandateRole()))
                .orElse(false);
    }

    private boolean contains(List<AmendmentItem> items, AmendmentType type) {
        return items.stream().anyMatch(i -> i.getType() == type);
    }

    private void applyOrder(List<AmendmentItem> items, List<String> order) {
        for (AmendmentItem item : items) {
            int idx = order.indexOf(item.getType().name());
            item.setSequenceOrder(idx < 0 ? order.size() : idx);
        }
    }
}
