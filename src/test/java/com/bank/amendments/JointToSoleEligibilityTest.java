package com.bank.amendments;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.internal.utils.DMNRuntimeBuilder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Decision tests for the joint to sole child eligibility model.
 *
 * These cover the cases that make joint to sole the highest risk journey:
 * bereavement and incapacity divert to a different journey entirely rather
 * than being processed as a conversion.
 */
@DisplayName("Joint to sole eligibility decision")
class JointToSoleEligibilityTest {

    private static final String NAMESPACE = "https://bank.com/dmn/amendments";
    private static final String MODEL_NAME = "JointToSoleEligibility";

    private static DMNRuntime runtime;
    private static DMNModel model;

    @BeforeAll
    static void setUp() {
        runtime = DMNRuntimeBuilder.fromDefaults()
                .buildConfiguration()
                .fromClasspathResource("decisions/joint-to-sole-eligibility.dmn", JointToSoleEligibilityTest.class)
                .getOrElseThrow(RuntimeException::new);
        model = runtime.getModel(NAMESPACE, MODEL_NAME);
        assertThat(model).isNotNull();
    }

    private Map<String, Object> baseline() {
        Map<String, Object> in = new HashMap<>();
        in.put("AnyPartyDeceased", false);
        in.put("AnyPartyIncapacitated", false);
        in.put("AllConsentsGranted", true);
        in.put("JointLiabilities", false);
        in.put("OverdraftBalance", BigDecimal.ZERO);
        in.put("LinkedProducts", false);
        in.put("RemainingPartyEligible", true);
        return in;
    }

    private Map<String, Object> evaluate(Map<String, Object> inputs) {
        DMNContext context = runtime.newContext();
        inputs.forEach(context::set);
        DMNResult result = runtime.evaluateAll(model, context);
        assertThat(result.hasErrors()).isFalse();

        @SuppressWarnings("unchecked")
        Map<String, Object> outcome =
                (Map<String, Object>) result.getDecisionResultByName("JtsEligibility").getResult();
        return outcome;
    }

    @Test
    @DisplayName("clean conversion with all consents is eligible")
    void cleanConversionEligible() {
        Map<String, Object> out = evaluate(baseline());
        assertThat(out.get("outcome")).isEqualTo("ELIGIBLE");
        assertThat(out.get("requiresUnderwriting")).isEqualTo(false);
    }

    @Test
    @DisplayName("deceased party diverts to bereavement, never processed as conversion")
    void deceasedPartyDiverts() {
        Map<String, Object> in = baseline();
        in.put("AnyPartyDeceased", true);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("DIVERTED");
        assertThat(out.get("reasonCode")).isEqualTo("PARTY_DECEASED");
        assertThat(out.get("divertToJourney")).isEqualTo("BEREAVEMENT");
    }

    @Test
    @DisplayName("deceased party takes precedence over every other condition")
    void deceasedTakesPrecedence() {
        Map<String, Object> in = baseline();
        in.put("AnyPartyDeceased", true);
        in.put("AllConsentsGranted", false);
        in.put("JointLiabilities", true);
        in.put("RemainingPartyEligible", false);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("DIVERTED");
        assertThat(out.get("divertToJourney")).isEqualTo("BEREAVEMENT");
    }

    @Test
    @DisplayName("incapacitated party diverts to power of attorney journey")
    void incapacitatedPartyDiverts() {
        Map<String, Object> in = baseline();
        in.put("AnyPartyIncapacitated", true);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("DIVERTED");
        assertThat(out.get("divertToJourney")).isEqualTo("POWER_OF_ATTORNEY");
    }

    @Test
    @DisplayName("outstanding consent blocks conversion")
    void outstandingConsentBlocks() {
        Map<String, Object> in = baseline();
        in.put("AllConsentsGranted", false);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("BLOCKED");
        assertThat(out.get("reasonCode")).isEqualTo("CONSENT_OUTSTANDING");
    }

    @Test
    @DisplayName("joint liabilities require underwriting review")
    void jointLiabilitiesRequireUnderwriting() {
        Map<String, Object> in = baseline();
        in.put("JointLiabilities", true);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("JOINT_LIABILITY_PRESENT");
        assertThat(out.get("requiresUnderwriting")).isEqualTo(true);
    }

    @Test
    @DisplayName("overdrawn balance requires underwriting review")
    void overdrawnRequiresUnderwriting() {
        Map<String, Object> in = baseline();
        in.put("OverdraftBalance", new BigDecimal("250.00"));

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("OVERDRAWN_BALANCE");
        assertThat(out.get("requiresUnderwriting")).isEqualTo(true);
    }

    @Test
    @DisplayName("ineligible sole holder requires review")
    void ineligibleSoleHolderReviewed() {
        Map<String, Object> in = baseline();
        in.put("RemainingPartyEligible", false);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("SOLE_HOLDER_NOT_ELIGIBLE");
    }

    @Test
    @DisplayName("linked products require review without underwriting")
    void linkedProductsReviewed() {
        Map<String, Object> in = baseline();
        in.put("LinkedProducts", true);

        Map<String, Object> out = evaluate(in);
        assertThat(out.get("outcome")).isEqualTo("REVIEW_REQUIRED");
        assertThat(out.get("reasonCode")).isEqualTo("LINKED_PRODUCTS_PRESENT");
        assertThat(out.get("requiresUnderwriting")).isEqualTo(false);
    }
}
