package com.bank.amendments.handler;

import org.kie.kogito.process.impl.DefaultWorkItemHandlerConfig;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.List;

/**
 * Registers WorkItemHandlers with the embedded engine.
 *
 * Core banking is a real handler: the shipped REST handler cannot express the
 * transport, so the integration is encapsulated in CoreBankingWorkItemHandler.
 *
 * The remaining task types are registered against StubWorkItemHandler, which
 * logs and completes. Each is a genuine integration point that still needs
 * implementing — replace them as the downstream services become available.
 */
@Component
public class WorkItemHandlerConfig extends DefaultWorkItemHandlerConfig {

    /** Task types with no real integration yet. */
    static final List<String> STUBBED = List.of(
            "SanctionsScreeningTask",
            "CustomerNotificationTask",
            "DocumentVerificationTask",
            "RequestCancellationTask",
            "SlaEscalationTask",
            "AddressValidationTask",
            "AddressVerificationTask",
            "TaxResidencyTask",
            "EvidenceReminderTask",
            "EvidenceRequirementTask",
            "FailureRecordingTask",
            "CardReissueTask",
            "PartyResolutionTask",
            "JourneyDiversionTask",
            "ConsentRequestTask",
            "ConsentExpiryTask",
            "CompensationTask",
            "Receive Task");

    private final CoreBankingWorkItemHandler coreBanking;
    private final AsyncCoreBankingWorkItemHandler asyncCoreBanking;
    private final AccountIntakeWorkItemHandler accountIntake;
    private final DerivePlanWorkItemHandler derivePlan;
    private final ReadItemDetailWorkItemHandler readItemDetail;

    public WorkItemHandlerConfig(CoreBankingWorkItemHandler coreBanking,
                                 AsyncCoreBankingWorkItemHandler asyncCoreBanking,
                                 AccountIntakeWorkItemHandler accountIntake,
                                 DerivePlanWorkItemHandler derivePlan,
                                 ReadItemDetailWorkItemHandler readItemDetail) {
        this.coreBanking = coreBanking;
        this.asyncCoreBanking = asyncCoreBanking;
        this.accountIntake = accountIntake;
        this.derivePlan = derivePlan;
        this.readItemDetail = readItemDetail;
    }

    @PostConstruct
    public void init() {
        register(AccountIntakeWorkItemHandler.NAME, accountIntake);
        register(DerivePlanWorkItemHandler.NAME, derivePlan);
        register(ReadItemDetailWorkItemHandler.NAME, readItemDetail);
        register(CoreBankingWorkItemHandler.NAME, coreBanking);
        register(AsyncCoreBankingWorkItemHandler.NAME, asyncCoreBanking);

        for (String name : STUBBED) {
            register(name, new StubWorkItemHandler(name));
        }
    }
}
