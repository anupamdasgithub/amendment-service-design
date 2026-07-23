package com.bank.amendments.handler;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

/**
 * Adapter to the core banking platform. Encapsulates mutual TLS, the
 * bank message envelope and the idempotency contract.
 *
 * Working stub that models the contract so processes can be exercised
 * end to end without the real platform.
 */
@Component
public class CoreBankingClient {

    public CoreBankingResponse invoke(String operation,
                                      String accountId,
                                      String idempotencyKey,
                                      Map<String, Object> parameters) {

        if (accountId == null || accountId.isBlank()) {
            throw new CoreBankingException("Account identifier is required");
        }

        CoreBankingResponse response = new CoreBankingResponse();
        response.setSuccess(true);
        response.setReference("CORE-" + UUID.randomUUID());
        response.setAppliedAt(Instant.now());
        return response;
    }

    public String submitAsync(String operation, String accountId, String idempotencyKey) {
        return "EXT-" + UUID.randomUUID();
    }

    public void cancel(String idempotencyKey) {
        // Real implementation issues a reversal.
    }
}
