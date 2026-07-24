package com.bank.amendments.handler;

import com.bank.amendments.model.AmendmentItem;
import com.bank.amendments.model.AmendmentRequest;
import com.bank.amendments.model.Enums.Admissibility;
import com.bank.amendments.model.Enums.AmendmentStatus;
import com.bank.amendments.model.Enums.AmendmentType;
import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Applies the admissibility outcome to the case file and derives the values
 * the sequencing decision and the routing gateways read.
 *
 * This is deliberately a handler rather than a script task. The BPMN script
 * validator rejects any bare identifier that is not a process variable, so
 * class references such as {@code Enums} or {@code AmendmentItem} fail
 * validation whether or not the process declares imports. Plain Java in a
 * handler is not subject to that check.
 */
@Component
public class DerivePlanWorkItemHandler implements KogitoWorkItemHandler {

    public static final String NAME = "DerivePlanTask";

    private static final Logger log = LoggerFactory.getLogger(DerivePlanWorkItemHandler.class);

    @Override
    @SuppressWarnings("unchecked")
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        Object requestParam = workItem.getParameter("request");
        Object decision = workItem.getParameter("admissibility");

        String outcome = "PERMITTED";
        String reasonCode = null;

        // The decision returns a context: admissibility, reasonCode, reasonText.
        if (decision instanceof Map<?, ?> m) {
            Object o = m.get("admissibility");
            if (o != null) outcome = String.valueOf(o);
            Object r = m.get("reasonCode");
            if (r != null) reasonCode = String.valueOf(r);
        } else if (decision != null) {
            outcome = String.valueOf(decision);
        }

        Admissibility admissibility;
        try {
            admissibility = Admissibility.valueOf(outcome);
        } catch (IllegalArgumentException e) {
            log.warn("Unrecognised admissibility outcome '{}', treating as PERMITTED", outcome);
            admissibility = Admissibility.PERMITTED;
        }

        List<AmendmentItem> permitted = new ArrayList<>();
        if (requestParam instanceof AmendmentRequest request && request.getItems() != null) {
            for (AmendmentItem item : request.getItems()) {
                item.setAdmissibility(admissibility);
                item.setReasonCode(reasonCode);
                if (admissibility == Admissibility.REFUSED) {
                    item.setStatus(AmendmentStatus.REFUSED);
                } else {
                    item.setStatus(AmendmentStatus.RUNNING);
                    permitted.add(item);
                }
            }
        }

        boolean coa = permitted.stream().anyMatch(i -> i.getType() == AmendmentType.COA);
        boolean con = permitted.stream().anyMatch(i -> i.getType() == AmendmentType.CON);
        boolean jts = permitted.stream().anyMatch(i -> i.getType() == AmendmentType.JOINT_TO_SOLE);

        log.info("Derived plan outcome={} permitted={} coa={} con={} jointToSole={} decision={}",
                outcome, permitted.size(), coa, con, jts, decision);

        Map<String, Object> results = new HashMap<>();
        results.put("permittedCount", permitted.size());
        results.put("includesCoa", coa);
        results.put("includesCon", con);
        results.put("includesJointToSole", jts);
        results.put("anyPermitted", !permitted.isEmpty());
        results.put("amendmentItems", permitted);
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
