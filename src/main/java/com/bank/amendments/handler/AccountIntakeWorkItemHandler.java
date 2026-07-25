package com.bank.amendments.handler;

import com.bank.amendments.model.*;
import com.bank.amendments.model.*;
import org.kie.kogito.internal.process.runtime.KogitoWorkItem;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemHandler;
import org.kie.kogito.internal.process.runtime.KogitoWorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Builds the amendment request case file.
 *
 * This is the first task in the parent process and the only one that
 * constructs the {@link AmendmentRequest} the rest of the design reads from.
 * Every gateway in ADR-001 routes on data this handler produces:
 * {@code request.items[].admissibility}, {@code item.type},
 * {@code item.coa.addressVerified} and so on. Without it those conditions
 * have nothing to evaluate.
 *
 * In production this would call the customer and account systems of record.
 * Here it assembles the case file from the request variables and a small
 * amount of derived data, which is enough to exercise every routing path.
 * The shape it produces is the contract; only the source of the data changes
 * when the real integrations arrive.
 */
@Component
public class AccountIntakeWorkItemHandler implements KogitoWorkItemHandler {

    public static final String NAME = "AccountIntakeTask";

    private static final Logger log = LoggerFactory.getLogger(AccountIntakeWorkItemHandler.class);

    @Override
    public void executeWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        Map<String, Object> p = workItem.getParameters();

        String requestId = str(p.get("requestId"));
        if (requestId == null || requestId.isBlank()) {
            requestId = "REQ-" + UUID.randomUUID().toString().substring(0, 8);
        }

        AmendmentRequest request = new AmendmentRequest();
        request.setRequestId(requestId);
        request.setCustomerId(orDefault(str(p.get("customerId")), "CUST-0001"));
        request.setAccountId(orDefault(str(p.get("accountId")), "ACC-0001"));
        request.setRequestorPartyId(orDefault(str(p.get("requestorPartyId")), "PARTY-1"));
        request.setChannel(channel(str(p.get("channel"))));
        request.setSubmittedAt(Instant.now());
        request.setStatus(RequestStatus.SCREENING);

        request.setAccount(buildAccount(p, request));
        request.setScreening(buildScreening(p));
        request.setItems(buildItems(p, request));
        request.setRequestedTypes(request.getItems().stream()
                .map(AmendmentItem::getType).toList());

        request.getAudit().add(new AuditEntry(
                Instant.now(), "system", "INTAKE_COMPLETED",
                "Case file built with " + request.getItems().size() + " item(s)"));

        log.info("Intake built case file request={} account={} items={} screening={} addressVerified={}",
                request.getRequestId(), request.getAccountId(),
                request.getItems().stream().map(i -> i.getType().name()).toList(),
                request.getScreening().getOutcome(),
                request.getItems().stream()
                        .filter(i -> i.getCoa() != null)
                        .map(i -> String.valueOf(i.getCoa().isAddressVerified()))
                        .findFirst().orElse("n/a"));

        Map<String, Object> results = new HashMap<>();
        results.put("request", request);
        // Child processes need these to call core banking.
        results.put("accountId", request.getAccountId());
        results.put("requestId", request.getRequestId());
        manager.completeWorkItem(workItem.getStringId(), results);
    }

    /**
     * Account snapshot. A joint account carries two parties, so the
     * joint-to-sole path has something to resolve.
     */
    private AccountSnapshot buildAccount(Map<String, Object> p, AmendmentRequest request) {
        AccountSnapshot a = new AccountSnapshot();
        a.setAccountId(request.getAccountId());
        a.setProductCode(orDefault(str(p.get("productCode")), "CURRENT-01"));
        a.setStatus(accountStatus(str(p.get("accountStatus"))));
        a.setJoint(bool(p.get("accountIsJoint"), false));
        a.setRiskBand(riskBand(str(p.get("riskBand"))));
        a.setJurisdiction(orDefault(str(p.get("jurisdiction")), "GB"));
        a.setInFlightAmendment(bool(p.get("inFlightAmendment"), false));
        a.setOverdraftFacility(bool(p.get("overdraftFacility"), false));
        a.setOverdraftBalance(decimal(p.get("overdraftBalance")));
        a.setLinkedProducts(bool(p.get("linkedProducts"), false));

        Party requestor = new Party();
        requestor.setPartyId(request.getRequestorPartyId());
        requestor.setFullName(orDefault(str(p.get("requestorName")), "A Customer"));
        requestor.setStatus(PartyStatus.PARTY_ACTIVE);
        requestor.setRequestor(true);
        requestor.setRemainingHolder(true);
        // The admissibility decision refuses when the mandate is view only.
        requestor.setMandateRole(bool(p.get("mandatePermits"), true) ? "FULL" : "VIEW_ONLY");
        a.getParties().add(requestor);

        if (a.isJoint()) {
            Party other = new Party();
            other.setPartyId("PARTY-2");
            other.setFullName(orDefault(str(p.get("otherPartyName")), "B Customer"));
            other.setStatus(partyStatus(str(p.get("otherPartyStatus"))));
            other.setRemainingHolder(false);
            other.setMandateRole("FULL");
            a.getParties().add(other);
        }

        // The design refuses when the requestor is not a party at all.
        if (!bool(p.get("requestorIsParty"), true)) {
            a.getParties().removeIf(Party::isRequestor);
        }
        return a;
    }

    private ScreeningResult buildScreening(Map<String, Object> p) {
        ScreeningResult s = new ScreeningResult();
        s.setOutcome(screening(str(p.get("screeningOutcome"))));
        s.setReferenceId("SCR-" + UUID.randomUUID().toString().substring(0, 8));
        s.setScreenedAt(Instant.now());
        return s;
    }

    /**
     * One item per requested amendment type, each with the type-specific
     * detail its child process reads.
     */
    private List<AmendmentItem> buildItems(Map<String, Object> p, AmendmentRequest request) {
        List<AmendmentItem> items = new ArrayList<>();

        // A single amendmentType is the common case; includesX flags allow a
        // multi-amendment request to fan out to several children.
        List<AmendmentType> types = new ArrayList<>();
        AmendmentType single = amendmentType(str(p.get("amendmentType")));
        if (single != null) types.add(single);
        if (bool(p.get("includesCoa"), false) && !types.contains(AmendmentType.COA))
            types.add(AmendmentType.COA);
        if (bool(p.get("includesCon"), false) && !types.contains(AmendmentType.CON))
            types.add(AmendmentType.CON);
        if (bool(p.get("includesJointToSole"), false) && !types.contains(AmendmentType.JOINT_TO_SOLE))
            types.add(AmendmentType.JOINT_TO_SOLE);
        if (types.isEmpty()) types.add(AmendmentType.COA);

        for (AmendmentType t : types) {
            AmendmentItem item = new AmendmentItem();
            item.setItemId(UUID.randomUUID().toString());
            item.setType(t);
            item.setStatus(AmendmentStatus.PENDING);
            item.setStartedAt(Instant.now());
            switch (t) {
                case COA -> item.setCoa(buildCoa(p));
                case CON -> item.setCon(buildCon(p));
                case JOINT_TO_SOLE -> item.setJointToSole(buildJts(p, request));
            }
            items.add(item);
        }
        return items;
    }

    private CoaDetail buildCoa(Map<String, Object> p) {
        CoaDetail c = new CoaDetail();

        Address current = new Address();
        current.setLine1(orDefault(str(p.get("currentAddressLine1")), "1 Old Street"));
        current.setCity(orDefault(str(p.get("currentAddressCity")), "London"));
        current.setPostcode(orDefault(str(p.get("currentAddressPostcode")), "EC1V 9XX"));
        current.setCountryCode("GB");
        c.setCurrentAddress(current);

        Address next = new Address();
        next.setLine1(orDefault(str(p.get("newAddressLine1")), "2 New Street"));
        next.setCity(orDefault(str(p.get("newAddressCity")), "Manchester"));
        next.setPostcode(orDefault(str(p.get("newAddressPostcode")), "M1 1AA"));
        next.setCountryCode(orDefault(str(p.get("newAddressCountry")), "GB"));
        c.setNewAddress(next);

        // Drives the Verified gateway: false sends the instance down the
        // evidence branch and parks it at a user task.
        c.setAddressVerified(bool(p.get("addressVerified"), true));
        c.setEvidenceStatus(c.isAddressVerified()
                ? EvidenceStatus.NOT_REQUIRED : EvidenceStatus.OUTSTANDING);
        c.setCorrespondenceOnly(bool(p.get("correspondenceOnly"), false));
        c.setTaxResidencyImpact(!"GB".equals(next.getCountryCode()));
        c.setCrsReassessmentRequired(c.isTaxResidencyImpact());
        return c;
    }

    private ConDetail buildCon(Map<String, Object> p) {
        ConDetail c = new ConDetail();
        c.setCurrentName(orDefault(str(p.get("currentName")), "A Customer"));
        c.setNewName(orDefault(str(p.get("newName")), "A Newname"));
        c.setReason(nameChangeReason(str(p.get("nameChangeReason"))));
        c.setEvidenceDocumentType(evidenceFor(c.getReason()));
        c.setEvidenceStatus(EvidenceStatus.OUTSTANDING);
        c.setRescreenRequired(true);

        // The screening gateway reads this; the child re-screens under the
        // new name and refers a hit to financial crime.
        ScreeningResult rescreen = new ScreeningResult();
        rescreen.setOutcome(screening(str(p.get("rescreenOutcome"))));
        rescreen.setReferenceId("SCR-" + UUID.randomUUID().toString().substring(0, 8));
        rescreen.setScreenedAt(Instant.now());
        c.setRescreenResult(rescreen);

        c.setCardReissueRequired(true);
        return c;
    }

    private JointToSoleDetail buildJts(Map<String, Object> p, AmendmentRequest request) {
        JointToSoleDetail j = new JointToSoleDetail();
        j.setRemainingPartyId(request.getRequestorPartyId());

        List<String> departing = new ArrayList<>();
        List<ConsentRecord> consents = new ArrayList<>();
        for (Party party : request.getAccount().getParties()) {
            if (party.isRequestor()) continue;
            departing.add(party.getPartyId());

            ConsentRecord consent = new ConsentRecord();
            consent.setPartyId(party.getPartyId());
            consent.setStatus(bool(p.get("allConsentsGranted"), true)
                    ? ConsentStatus.GRANTED : ConsentStatus.REQUESTED);
            consent.setRequestedAt(Instant.now());
            if (consent.getStatus() == ConsentStatus.GRANTED) {
                consent.setRespondedAt(Instant.now());
            }
            consents.add(consent);
        }
        j.setDepartingPartyIds(departing);
        j.setConsents(consents);
        j.setAllConsentsGranted(!consents.isEmpty()
                && consents.stream().allMatch(c -> c.getStatus() == ConsentStatus.GRANTED));

        // These drive the eligibility DMN: a deceased or incapacitated party
        // diverts to bereavement or power of attorney rather than converting.
        j.setAnyPartyDeceased(request.getAccount().getParties().stream()
                .anyMatch(x -> x.getStatus() == PartyStatus.DECEASED));
        j.setAnyPartyIncapacitated(request.getAccount().getParties().stream()
                .anyMatch(x -> x.getStatus() == PartyStatus.INCAPACITATED));
        j.setJointLiabilities(bool(p.get("jointLiabilities"), false));
        j.setOutstandingBalance(decimal(p.get("outstandingBalance")));
        j.setRemainingPartyEligible(bool(p.get("remainingPartyEligible"), true));
        return j;
    }

    @Override
    public void abortWorkItem(KogitoWorkItem workItem, KogitoWorkItemManager manager) {
        manager.abortWorkItem(workItem.getStringId());
    }

    @Override
    public String getName() {
        return NAME;
    }

    // ---- parameter coercion -------------------------------------------------
    // Work item parameters arrive as Object; each accessor falls back to a
    // value that keeps the happy path viable when the caller omits a field.

    private static String str(Object o) { return o == null ? null : String.valueOf(o); }

    private static String orDefault(String v, String fallback) {
        return (v == null || v.isBlank()) ? fallback : v;
    }

    private static boolean bool(Object o, boolean fallback) {
        if (o instanceof Boolean b) return b;
        if (o == null) return fallback;
        return Boolean.parseBoolean(String.valueOf(o));
    }

    private static double decimal(Object o) {
        if (o instanceof Number n) return n.doubleValue();
        if (o == null) return 0.0;
        try { return Double.parseDouble(String.valueOf(o)); }
        catch (NumberFormatException e) { return 0.0; }
    }

    private static <E extends Enum<E>> E enumOf(Class<E> type, String v, E fallback) {
        if (v == null || v.isBlank()) return fallback;
        try { return Enum.valueOf(type, v.trim().toUpperCase()); }
        catch (IllegalArgumentException e) { return fallback; }
    }

    private static Channel channel(String v) { return enumOf(Channel.class, v, Channel.DIGITAL); }
    private static AccountStatus accountStatus(String v) { return enumOf(AccountStatus.class, v, AccountStatus.ACTIVE); }
    private static PartyStatus partyStatus(String v) { return enumOf(PartyStatus.class, v, PartyStatus.PARTY_ACTIVE); }
    private static RiskBand riskBand(String v) { return enumOf(RiskBand.class, v, RiskBand.LOW); }
    private static ScreeningOutcome screening(String v) { return enumOf(ScreeningOutcome.class, v, ScreeningOutcome.CLEAR); }
    private static ConNameChangeReason nameChangeReason(String v) { return enumOf(ConNameChangeReason.class, v, ConNameChangeReason.DEED_POLL); }

    private static AmendmentType amendmentType(String v) {
        return (v == null || v.isBlank()) ? null : enumOf(AmendmentType.class, v, null);
    }

    private static String evidenceFor(ConNameChangeReason reason) {
        return switch (reason) {
            case MARRIAGE -> "MARRIAGE_CERTIFICATE";
            case CIVIL_PARTNERSHIP -> "CIVIL_PARTNERSHIP_CERTIFICATE";
            case DIVORCE -> "DECREE_ABSOLUTE";
            case DEED_POLL -> "DEED_POLL";
            case GENDER_RECOGNITION -> "GENDER_RECOGNITION_CERTIFICATE";
            case SPELLING_CORRECTION -> "PASSPORT";
        };
    }
}
