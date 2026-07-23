-- Application-owned case store.
--
-- Separate from the engine's process_instances table. The engine store holds
-- execution state; this store holds the business case file and the audit
-- trail, which must outlive the process instance for regulatory retention.

CREATE TABLE amendment_request (
    request_id          VARCHAR(36)  NOT NULL,
    customer_id         VARCHAR(50)  NOT NULL,
    account_id          VARCHAR(50)  NOT NULL,
    requestor_party_id  VARCHAR(50)  NOT NULL,
    channel             VARCHAR(20)  NOT NULL,
    status              VARCHAR(30)  NOT NULL,
    process_instance_id VARCHAR(36),
    submitted_at        TIMESTAMPTZ  NOT NULL,
    sla_due_at          TIMESTAMPTZ,
    closed_at           TIMESTAMPTZ,
    cancellation_reason VARCHAR(255),
    created_at          TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at          TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT pk_amendment_request PRIMARY KEY (request_id)
);

CREATE INDEX idx_request_customer   ON amendment_request (customer_id);
CREATE INDEX idx_request_account    ON amendment_request (account_id);
CREATE INDEX idx_request_status     ON amendment_request (status);
CREATE INDEX idx_request_sla        ON amendment_request (sla_due_at)
    WHERE status NOT IN ('COMPLETED', 'REFUSED', 'CANCELLED');

CREATE TABLE amendment_item (
    item_id                   VARCHAR(36)  NOT NULL,
    request_id                VARCHAR(36)  NOT NULL,
    amendment_type            VARCHAR(20)  NOT NULL,
    status                    VARCHAR(30)  NOT NULL,
    admissibility             VARCHAR(20),
    reason_code               VARCHAR(50),
    reason_text               VARCHAR(255),
    sequence_order            INTEGER,
    child_process_instance_id VARCHAR(36),
    detail                    JSONB,
    started_at                TIMESTAMPTZ,
    completed_at              TIMESTAMPTZ,
    created_at                TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at                TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT pk_amendment_item PRIMARY KEY (item_id),
    CONSTRAINT fk_item_request FOREIGN KEY (request_id)
        REFERENCES amendment_request (request_id) ON DELETE RESTRICT,
    CONSTRAINT ck_item_type CHECK (amendment_type IN ('COA', 'CON', 'JOINT_TO_SOLE'))
);

CREATE INDEX idx_item_request ON amendment_item (request_id);
CREATE INDEX idx_item_type    ON amendment_item (amendment_type, status);
CREATE INDEX idx_item_child   ON amendment_item (child_process_instance_id);

CREATE TABLE amendment_party_consent (
    consent_id      VARCHAR(36)  NOT NULL,
    item_id         VARCHAR(36)  NOT NULL,
    party_id        VARCHAR(50)  NOT NULL,
    status          VARCHAR(20)  NOT NULL,
    requested_at    TIMESTAMPTZ,
    responded_at    TIMESTAMPTZ,
    expires_at      TIMESTAMPTZ,
    channel         VARCHAR(20),
    decline_reason  VARCHAR(255),
    evidence_ref    VARCHAR(255),
    CONSTRAINT pk_party_consent PRIMARY KEY (consent_id),
    CONSTRAINT fk_consent_item FOREIGN KEY (item_id)
        REFERENCES amendment_item (item_id) ON DELETE RESTRICT,
    CONSTRAINT uq_consent_item_party UNIQUE (item_id, party_id)
);

CREATE INDEX idx_consent_expiry ON amendment_party_consent (expires_at)
    WHERE status = 'REQUESTED';

CREATE TABLE amendment_document (
    document_id      VARCHAR(36)  NOT NULL,
    request_id       VARCHAR(36)  NOT NULL,
    item_id          VARCHAR(36),
    dms_uri          VARCHAR(500) NOT NULL,
    document_type    VARCHAR(50)  NOT NULL,
    status           VARCHAR(20)  NOT NULL,
    uploaded_at      TIMESTAMPTZ,
    rejection_reason VARCHAR(255),
    CONSTRAINT pk_amendment_document PRIMARY KEY (document_id),
    CONSTRAINT fk_document_request FOREIGN KEY (request_id)
        REFERENCES amendment_request (request_id) ON DELETE RESTRICT
);

CREATE INDEX idx_document_request ON amendment_document (request_id);

-- Append-only. No UPDATE or DELETE grants in any environment.
CREATE TABLE amendment_audit (
    audit_id     BIGSERIAL    NOT NULL,
    request_id   VARCHAR(36)  NOT NULL,
    item_id      VARCHAR(36),
    occurred_at  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    actor        VARCHAR(100) NOT NULL,
    action       VARCHAR(80)  NOT NULL,
    detail       JSONB,
    CONSTRAINT pk_amendment_audit PRIMARY KEY (audit_id)
);

CREATE INDEX idx_audit_request  ON amendment_audit (request_id, occurred_at);
CREATE INDEX idx_audit_occurred ON amendment_audit (occurred_at);
