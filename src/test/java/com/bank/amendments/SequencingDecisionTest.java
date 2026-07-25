package com.bank.amendments;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.core.internal.utils.DMNRuntimeBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Decision tests for AmendmentSequencing.
 *
 * Parallel execution is not automatic. Anything paired with a joint to sole
 * conversion must be ordered before it so identity and address are settled
 * before the account restructures.
 */
@DisplayName("Amendment sequencing decision")
class SequencingDecisionTest {

    private static final String NAMESPACE = "https://bank.com/dmn/amendments";
    private static final String MODEL_NAME = "AmendmentSequencing";

    private static DMNRuntime runtime;
    private static DMNModel model;

    @BeforeAll
    static void setUp() {
        runtime = DMNRuntimeBuilder.fromDefaults()
                .buildConfiguration()
                .fromClasspathResource("decisions/amendment-sequencing.dmn", SequencingDecisionTest.class)
                .getOrElseThrow(RuntimeException::new);
        model = runtime.getModel(NAMESPACE, MODEL_NAME);
        assertThat(model).isNotNull();
    }

    private Map<String, Object> evaluate(int count, boolean jts, boolean con, boolean coa) {
        Map<String, Object> in = new HashMap<>();
        in.put("permittedCount", count);
        in.put("includesJointToSole", jts);
        in.put("includesCon", con);
        in.put("includesCoa", coa);

        DMNContext context = runtime.newContext();
        in.forEach(context::set);
        DMNResult result = runtime.evaluateAll(model, context);
        assertThat(result.hasErrors()).isFalse();

        @SuppressWarnings("unchecked")
        Map<String, Object> outcome =
                (Map<String, Object>) result.getDecisionResultByName("ExecutionPlan").getResult();
        return outcome;
    }

    @Test
    @DisplayName("single amendment needs no sequencing")
    void singleAmendmentParallel() {
        Map<String, Object> out = evaluate(1, false, false, true);
        assertThat(out.get("mode")).isEqualTo("PARALLEL");
    }

    @Test
    @DisplayName("CoA and CoN together run in parallel")
    void coaAndConParallel() {
        Map<String, Object> out = evaluate(2, false, true, true);
        assertThat(out.get("mode")).isEqualTo("PARALLEL");
    }

    @Test
    @DisplayName("CoN with joint to sole is sequenced, name first")
    void conBeforeJointToSole() {
        Map<String, Object> out = evaluate(2, true, true, false);
        assertThat(out.get("mode")).isEqualTo("SEQUENTIAL");
        assertThat(out.get("orderedSequence")).isEqualTo("CON,JOINT_TO_SOLE");
    }

    @Test
    @DisplayName("CoA with joint to sole is sequenced, address first")
    void coaBeforeJointToSole() {
        Map<String, Object> out = evaluate(2, true, false, true);
        assertThat(out.get("mode")).isEqualTo("SEQUENTIAL");
        assertThat(out.get("orderedSequence")).isEqualTo("COA,JOINT_TO_SOLE");
    }

    @Test
    @DisplayName("all three are sequenced with conversion last")
    void allThreeSequenced() {
        Map<String, Object> out = evaluate(3, true, true, true);
        assertThat(out.get("mode")).isEqualTo("SEQUENTIAL");
        assertThat(out.get("orderedSequence")).isEqualTo("CON,COA,JOINT_TO_SOLE");
        assertThat((String) out.get("orderedSequence")).endsWith("JOINT_TO_SOLE");
    }
}
