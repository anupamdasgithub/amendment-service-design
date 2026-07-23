package com.bank.amendments.handler;

import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Asynchronous variant. Demonstrates the wait-state lifecycle:
 * executeWorkItem returns without completing, the work item parks as a
 * wait state and the instance is checkpointed to the persistence store.
 * Completion arrives later via the callback, which rehydrates the instance.
 */
@Component
public class AsyncCoreBankingWorkItemHandler implements KogitoWorkItemHandler {

    public static final String NAME = "AsyncCoreBankingTask";

    private static final Logger log = LoggerFactory.getLogger(AsyncCoreBankingWorkItemHandler.class);

    private final CoreBankingClient client;
    private final Map<String, PendingWork> pending = new ConcurrentHashMap<>();

    public AsyncCoreBankingWorkItemHandler(CoreBankingClient client) {
        this.client = client;
    }

    @Override
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        String operation = (String) workItem.getParameter("operation");
        String accountId = (String) workItem.getParameter("accountId");
        String idempotencyKey = (String) workItem.getParameter("idempotencyKey");

        String externalRef = client.submitAsync(operation, accountId, idempotencyKey);
        pending.put(externalRef, new PendingWork(workItem.getStringId(), manager));

        log.info("Submitted async core op={} externalRef={} — work item parked", operation, externalRef);
    }

    /**
     * Invoked by the inbound callback listener when core banking reports
     * the outcome. Completing here resumes the parked instance.
     */
    public void onCallback(String externalRef, boolean success, String reference, String failureCode) {
        PendingWork work = pending.remove(externalRef);
        if (work == null) {
            log.warn("Callback for unknown externalRef={} — ignoring", externalRef);
            return;
        }

        Map<String, Object> results = new HashMap<>();
        results.put("success", success);
        results.put("coreReference", reference);
        if (!success) {
            results.put("failureCode", failureCode);
        }

        log.info("Completing parked work item externalRef={} success={}", externalRef, success);
        work.manager.completeWorkItem(work.workItemId, results);
    }

    @Override
    public void abortWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        pending.values().removeIf(w -> w.workItemId.equals(workItem.getStringId()));
        manager.abortWorkItem(workItem.getStringId());
    }

    @Override
    public String getName() {
        return NAME;
    }

    private static final class PendingWork {
        final String workItemId;
        final KogitoWorkItemManager manager;

        PendingWork(String workItemId, KogitoWorkItemManager manager) {
            this.workItemId = workItemId;
            this.manager = manager;
        }
    }
}
