package com.bank.amendments.handler;

import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;
import org.kie.kogito.process.workitem.WorkItemExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Custom WorkItemHandler for the proprietary core banking adapter.
 *
 * This is the genuine custom-handler case: the shipped REST Service Call
 * handler cannot express the core banking transport (mutual TLS plus a
 * bank-specific message envelope and idempotency contract), so the
 * integration is encapsulated here and exposed to BPMN as a task type.
 *
 * Synchronous completion. For long-running core operations see
 * AsyncCoreBankingWorkItemHandler.
 */
@Component
public class CoreBankingWorkItemHandler implements KogitoWorkItemHandler {

    public static final String NAME = "CoreBankingTask";

    private static final Logger log = LoggerFactory.getLogger(CoreBankingWorkItemHandler.class);

    private final CoreBankingClient client;

    public CoreBankingWorkItemHandler(CoreBankingClient client) {
        this.client = client;
    }

    @Override
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        String operation = (String) workItem.getParameter("operation");
        String accountId = (String) workItem.getParameter("accountId");
        String idempotencyKey = (String) workItem.getParameter("idempotencyKey");

        log.info("Core banking call op={} account={} key={}", operation, accountId, idempotencyKey);

        Map<String, Object> results = new HashMap<>();
        try {
            CoreBankingResponse response = client.invoke(
                    operation, accountId, idempotencyKey, workItem.getParameters());

            results.put("success", response.isSuccess());
            results.put("coreReference", response.getReference());
            results.put("appliedAt", response.getAppliedAt());

            if (!response.isSuccess()) {
                results.put("failureCode", response.getFailureCode());
                results.put("failureText", response.getFailureText());
            }

            manager.completeWorkItem(workItem.getStringId(), results);

        } catch (CoreBankingException e) {
            log.error("Core banking call failed op={} account={}", operation, accountId, e);
            throw new WorkItemExecutionException("CORE_BANKING_FAILURE", e.getMessage());
        }
    }

    @Override
    public void abortWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        String idempotencyKey = (String) workItem.getParameter("idempotencyKey");
        log.warn("Aborting core banking work item key={}", idempotencyKey);
        try {
            client.cancel(idempotencyKey);
        } catch (Exception e) {
            log.error("Cancel failed for key={}", idempotencyKey, e);
        }
        manager.abortWorkItem(workItem.getStringId());
    }

    @Override
    public String getName() {
        return NAME;
    }
}
