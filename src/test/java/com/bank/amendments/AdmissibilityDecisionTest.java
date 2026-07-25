package com.bank.amendments;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieRuntimeFactory;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.internal.utils.DMNRuntimeBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Decision tests for AmendmentAdmissibility.
 *
 * These run locally with `mvn test` against the .dmn on the classpath.
 * No deployment, no pipeline, no commit is required to test a rule change:
 * edit the decision table, rerun, see the result. Reserve commit and CI
 * for promotion to shared environments.
 */
@DisplayName("Amendment admissibility decision")
class AdmissibilityDecisionTest {

    private static final String NAMESPACE = "https://bank.com/dmn/amendments";
    private static final String MODEL_NAME = "AmendmentAdmissibility";

    private static DMNRuntime runtime;
    private static DMNModel model;

    @BeforeAll
    static void setUp() {
        runtime = DMNRuntimeBuilder.fromDefaults()
                .buildConfiguration()
                .fromClasspathResource("decisions/amendment-admissibility.dmn", AdmissibilityDecisionTest.class)
                .getOrElseThrow(RuntimeException::new);
        model = runtime.getModel(NAMESPACE, MODEL_NAME);
        assertThat(model).isNotNull();
    }

    private Map<String, Object> baseline() {
        Map<String, Object> in = new HashMap<>();
        in.put("amendmentType", "COA");
        in.put("accountStatus", "ACTIVE");
        in.put("requestorIsParty", true);
        in.put("mandatePermits", true);
        in.put("channel", "DIGITAL");
        in.put("screeningOutcome", "CLEAR");
        in.put("inFlightAmendment", false);
        in.put("accountIsJoint", false);
        in.put("riskBand", "LOW");
        return in;
    }

    private Map<String, Object> evaluate(Map<String, Object> inputs) {
        DMNContext context = runtime.newContext();
        inputs.forEach(context::set);
        DMNResult result = runtime.evaluateAll(model, context);
        assertThat(result.hasErrors()).isFalse();

        @SuppressWarnings("unchecked")
        Map<String, Object> outcome =
                (Map<String, Object>) result.getDecisionResultByName("Admissibility").getResult();
        return outcome;
    }

    @Test
    @DisplayName("clean CoA on an active account is permitted")
    void cleanCoaIsPermitted() {
        Map<String, Object> out = evaluate(baseline());
        assertThat(out.get("admissibility")).isEqualTo("PERMITTED");
        assertThat(out.get("reasonCode")).isEqualTo("OK");
    }

    @Test
    @DisplayName("non-party requestor is refused before any other check")
    void nonPartyRequestorRefused() {
        Map<String, Object> in = baseline();
        in.put("requestorIsParty", false);
        // Deliberately also set a screening hit: FIRST hit policy means the
        // authority refusal wins, giving a deterministic reason code.
        in.put("screeningOutcome", "CONFIRMED_HIT");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REFUSED");
        assertThat(out.get("reasonCode")).isEqualTo("AUTH_NOT_PARTY");
    }

    @Test
    @DisplayName("insufficient mandate is refused")
    void insufficientMandateRefused() {
        Map<String, Object> in = baseline();
        in.put("mandatePermits", false);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REFUSED");
        assertThat(out.get("reasonCode")).isEqualTo("AUTH_MANDATE_INSUFFICIENT");
    }

    @Test
    @DisplayName("confirmed screening hit is refused and referred")
    void screeningHitRefused() {
        Map<String, Object> in = baseline();
        in.put("screeningOutcome", "CONFIRMED_HIT");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REFUSED");
        assertThat(out.get("reasonCode")).isEqualTo("SCREENING_HIT");
    }

    @Test
    @DisplayName("inconclusive screening routes to review, not refusal")
    void inconclusiveScreeningReviewed() {
        Map<String, Object> in = baseline();
        in.put("screeningOutcome", "POTENTIAL_MATCH");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("SCREENING_INCONCLUSIVE");
    }

    @Test
    @DisplayName("screening service unavailable routes to review, never auto-permits")
    void screeningUnavailableReviewed() {
        Map<String, Object> in = baseline();
        in.put("screeningOutcome", "UNAVAILABLE");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REVIEW_REQUIRED");
    }

    @Test
    @DisplayName("closed account cannot be amended")
    void closedAccountRefused() {
        Map<String, Object> in = baseline();
        in.put("accountStatus", "CLOSED");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REFUSED");
        assertThat(out.get("reasonCode")).isEqualTo("ACCOUNT_NOT_AMENDABLE");
    }

    @Test
    @DisplayName("dormant account routes to review rather than refusal")
    void dormantAccountReviewed() {
        Map<String, Object> in = baseline();
        in.put("accountStatus", "DORMANT");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("ACCOUNT_RESTRICTED");
    }

    @Test
    @DisplayName("conflicting in-flight amendment is refused")
    void inFlightConflictRefused() {
        Map<String, Object> in = baseline();
        in.put("inFlightAmendment", true);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REFUSED");
        assertThat(out.get("reasonCode")).isEqualTo("CONFLICTING_IN_FLIGHT");
    }

    @Test
    @DisplayName("joint to sole on a sole account is refused")
    void jointToSoleOnSoleAccountRefused() {
        Map<String, Object> in = baseline();
        in.put("amendmentType", "JOINT_TO_SOLE");
        in.put("accountIsJoint", false);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REFUSED");
        assertThat(out.get("reasonCode")).isEqualTo("NOT_JOINT_ACCOUNT");
    }

    @Test
    @DisplayName("digital joint to sole requires assisted review")
    void digitalJointToSoleReviewed() {
        Map<String, Object> in = baseline();
        in.put("amendmentType", "JOINT_TO_SOLE");
        in.put("accountIsJoint", true);
        in.put("channel", "DIGITAL");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("CHANNEL_REQUIRES_REVIEW");
    }

    @Test
    @DisplayName("branch joint to sole on a low risk account is permitted")
    void branchJointToSolePermitted() {
        Map<String, Object> in = baseline();
        in.put("amendmentType", "JOINT_TO_SOLE");
        in.put("accountIsJoint", true);
        in.put("channel", "BRANCH");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("PERMITTED");
    }

    @Test
    @DisplayName("high risk band requires review even when all else is clean")
    void highRiskReviewed() {
        Map<String, Object> in = baseline();
        in.put("riskBand", "HIGH");

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("admissibility")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("HIGH_RISK_CUSTOMER");
    }

    @Test
    @DisplayName("every outcome carries a reason code for audit")
    void everyOutcomeHasReasonCode() {
        for (String status : new String[]{"ACTIVE", "DORMANT", "FROZEN", "BLOCKED", "CLOSED"}) {
            Map<String, Object> in = baseline();
            in.put("accountStatus", status);
            Map<String, Object> out = evaluate(in);
            assertThat(out.get("reasonCode")).as("reason code for %s", status).isNotNull();
            assertThat(out.get("reasonText")).as("reason text for %s", status).isNotNull();
        }
    }
}
