# Request bodies

These are the JSON request bodies used by
`docs/amendment-service.postman_collection.json`, extracted as individual
files so they can be diffed, reused from curl, or posted from any client.

All content is synthetic and illustrative (see the project root README).

## Parent journeys — POST to `/amendment_request`

| File | Journey / outcome |
|---|---|
| `1-verified-address-straight-through.json` | CoA, `addressVerified: true` — applies and completes |
| `2-unverified-address-parks-at-a-human-task.json` | CoA, `addressVerified: false` — parks at *Request proof of address* |
| `3-change-of-name-screening-and-approval.json` | CoN — screening then maker-checker |
| `4-name-change-with-a-screening-hit.json` | CoN with a screening hit — financial-crime review |
| `5-joint-to-sole-consent-granted.json` | JTS, `jointLiabilities: false` — consent branch |
| `5b-joint-to-sole-underwriting-review.json` | JTS, `jointLiabilities: true` — routes to *Underwriting review* |
| `6-joint-to-sole-party-deceased-diverted.json` | JTS, deceased party — diverted to bereavement journey |
| `7-multiple-amendments-in-one-request.json` | Several items — sequenced or parallel per the sequencing decision |

## Task completion

| File | Use |
|---|---|
| `complete-the-proof-of-address-task.json` | Body for completing the CoA evidence task (usually `{}`) |

## Standalone decision inputs (DMN endpoints)

| File | Decision |
|---|---|
| `admissibility-permitted.json` | Admissibility — permitted case |
| `admissibility-refused-requestor-not-a-party.json` | Admissibility — refused, requestor not a party |
| `admissibility-refused-on-a-screening-hit.json` | Admissibility — refused, screening hit |
| `sequencing-name-change-before-conversion.json` | Sequencing — CoN ordered before JTS |
| `joint-to-sole-eligibility-deceased-party.json` | JTS eligibility — deceased party |

## Events / signals

| File | Use |
|---|---|
| `cancel-the-request.json` | Cancellation signal payload |
| `signal-sla-breached.json` | SLA-breach signal payload |

## Input field reference (parent journeys)

Admissibility inputs are camelCase and map directly to the
`AmendmentAdmissibility` DMN inputs: `amendmentType`, `accountStatus`,
`requestorIsParty`, `mandatePermits`, `channel`, `screeningOutcome`,
`inFlightAmendment`, `accountIsJoint`, `riskBand`.

Joint-to-sole eligibility inputs (populated on the item detail):
`anyPartyDeceased`, `anyPartyIncapacitated`, `allConsentsGranted`,
`jointLiabilities`, `overdraftBalance`, `linkedProducts`,
`remainingPartyEligible`. Setting `jointLiabilities: true` (with no deceased
or incapacitated party) yields `REVIEW_REQUIRED`, routing the instance to
*Underwriting review*.
