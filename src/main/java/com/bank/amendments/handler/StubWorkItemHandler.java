package com.bank.amendments.handler;

import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Placeholder handler for tasks whose real integration is not built yet.
 *
 * Logs the invocation and its parameters, then completes immediately so a
 * process instance can be driven end to end. Every task bound to one of these
 * is a genuine integration point that still needs implementing.
 *
 * It must not reach any shared environment: a stub that silently succeeds
 * would make a failed core banking call look like a successful amendment.
 */
public class StubWorkItemHandler implements KogitoWorkItemHandler {

    private static final Logger log = LoggerFactory.getLogger(StubWorkItemHandler.class);

    private final String name;

    public StubWorkItemHandler(String name) {
        this.name = name;
    }

    @Override
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        String node = workItem.getNodeInstance() != null
                ? workItem.getNodeInstance().getNodeName() : "unknown";

        // Copy into a plain map: the engine's ProxyMap does not print usefully.
        Map<String, Object> params = new HashMap<>();
        try {
            params.putAll(workItem.getParameters());
        } catch (Exception e) {
            log.warn("could not read parameters: {}", e.toString());
        }

        log.warn("STUB '{}' at node '{}' params={}", name, node, params);

        Map<String, Object> results = new HashMap<>();
        // Echo inputs back: a task whose output is mapped to a process
        // variable would otherwise null it when the stub returns nothing.
        params.forEach((k, v) -> {
            if (!"NodeName".equals(k) && v != null) results.put(k, v);
        });
        results.put("stubbed", Boolean.TRUE);
        results.put("handler", name);
        manager.completeWorkItem(workItem.getStringId(), results);
    }

    @Override
    public void abortWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        log.warn("STUB '{}' aborted", name);
        manager.abortWorkItem(workItem.getStringId());
    }

    @Override
    public String getName() {
        return name;
    }
}
