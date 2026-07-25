package com.bank.amendments.handler;

import com.bank.amendments.model.AmendmentItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Extracts the values the gateways route on from the amendment item.
 *
 * FEEL cannot read Java object properties, so a gateway cannot evaluate
 * something like {@code item.coa.addressVerified} directly. This handler
 * copies the values it needs onto plain process variables the conditions can
 * read. One handler serves all four processes; each reads only the fields
 * relevant to its own gateways, and absent detail simply yields a default.
 */
@Component
public class ReadItemDetailWorkItemHandler implements KogitoWorkItemHandler {

    public static final String NAME = "ReadItemDetailTask";

    private static final Logger log = LoggerFactory.getLogger(ReadItemDetailWorkItemHandler.class);

    @Override
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        Object raw = workItem.getParameter("item");
        Object eligibility = workItem.getParameter("JtsEligibility");

        String itemType = "COA";
        boolean addressVerified = false;
        String rescreenOutcome = "CLEAR";
        boolean allConsentsGranted = false;

        boolean anyPartyDeceased = false;
        boolean anyPartyIncapacitated = false;
        boolean jointLiabilities = false;
        java.math.BigDecimal overdraftBalance = java.math.BigDecimal.ZERO;
        boolean linkedProducts = false;
        boolean remainingPartyEligible = false;

        if (raw instanceof AmendmentItem item) {
            if (item.getType() != null) {
                itemType = item.getType().name();
            }
            if (item.getCoa() != null) {
                addressVerified = item.getCoa().isAddressVerified();
            }
            if (item.getCon() != null && item.getCon().getRescreenResult() != null
                    && item.getCon().getRescreenResult().getOutcome() != null) {
                rescreenOutcome = item.getCon().getRescreenResult().getOutcome().name();
            }
            if (item.getJointToSole() != null) {
                var jts = item.getJointToSole();
                allConsentsGranted = jts.isAllConsentsGranted();
                anyPartyDeceased = jts.isAnyPartyDeceased();
                anyPartyIncapacitated = jts.isAnyPartyIncapacitated();
                jointLiabilities = jts.isJointLiabilities();
                overdraftBalance = jts.getOutstandingBalance();
                linkedProducts = jts.isLinkedProducts();
                remainingPartyEligible = jts.isRemainingPartyEligible();
            }
        } else if (raw != null) {
            log.warn("Expected an AmendmentItem but received {}", raw.getClass().getName());
        }

        log.info("Read item detail type={} addressVerified={} rescreen={} consents={}",
                itemType, addressVerified, rescreenOutcome, allConsentsGranted);

        // The joint-to-sole eligibility decision returns a context; flatten
        // its outcome for the same reason the item fields are flattened.
        String jtsOutcome = "ELIGIBLE";
        if (eligibility instanceof Map<?, ?> m && m.get("outcome") != null) {
            jtsOutcome = String.valueOf(m.get("outcome"));
        }

        Map<String, Object> results = new HashMap<>();
        results.put("jtsOutcome", jtsOutcome);
        results.put("jtsDiverted", "DIVERTED".equals(jtsOutcome));
        results.put("jtsReviewRequired", "REVIEW_REQUIRED".equals(jtsOutcome));
        results.put("jtsEligible", "ELIGIBLE".equals(jtsOutcome));
        results.put("itemType", itemType);
        // FEEL rejects single-quoted string literals and double quotes leak
        // unescaped into generated Java, so the routing gateway compares
        // booleans rather than strings.
        results.put("isCoa", "COA".equals(itemType));
        results.put("isCon", "CON".equals(itemType));
        results.put("isJointToSole", "JOINT_TO_SOLE".equals(itemType));
        results.put("addressVerified", addressVerified);
        results.put("rescreenOutcome", rescreenOutcome);
        results.put("rescreenClear", "CLEAR".equals(rescreenOutcome));
        results.put("allConsentsGranted", allConsentsGranted);
        results.put("AnyPartyDeceased", anyPartyDeceased);
        results.put("AnyPartyIncapacitated", anyPartyIncapacitated);
        results.put("AllConsentsGranted", allConsentsGranted);
        results.put("JointLiabilities", jointLiabilities);
        results.put("OverdraftBalance", overdraftBalance);
        results.put("LinkedProducts", linkedProducts);
        results.put("RemainingPartyEligible", remainingPartyEligible);
        manager.completeWorkItem(workItem.getStringId(), results);
    }

    @Override
    public void abortWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        manager.abortWorkItem(workItem.getStringId());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
