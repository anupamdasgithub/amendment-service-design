package com.bank.amendments.handler;

import org.kie.kogito.process.impl.DefaultWorkItemHandlerConfig;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

/**
 * Registers custom WorkItemHandlers with the embedded engine.
 *
 * Handlers not registered here fall through to the shipped BAMOE
 * WorkItemHandlers Library (REST Service Call, GenAI, AI Agent),
 * which are configured declaratively on the task rather than in code.
 */
@Component
public class WorkItemHandlerConfig extends DefaultWorkItemHandlerConfig {

    private final CoreBankingWorkItemHandler coreBanking;
    private final AsyncCoreBankingWorkItemHandler asyncCoreBanking;

    public WorkItemHandlerConfig(CoreBankingWorkItemHandler coreBanking,
                                 AsyncCoreBankingWorkItemHandler asyncCoreBanking) {
        this.coreBanking = coreBanking;
        this.asyncCoreBanking = asyncCoreBanking;
    }

    @PostConstruct
    public void init() {
        register(CoreBankingWorkItemHandler.NAME, coreBanking);
        register(AsyncCoreBankingWorkItemHandler.NAME, asyncCoreBanking);
    }
}
