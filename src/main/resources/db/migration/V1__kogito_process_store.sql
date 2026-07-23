-- Kogito JDBC process persistence.
--
-- These tables are the engine's own store: instance state is written here
-- at each wait state and read back to rehydrate an instance. Contrast with
-- a BAW shared BPMDB: this store is owned by this service alone.
--
-- Verify column definitions against the DDL shipped with the BAMOE release
-- in use before applying to a shared environment.

CREATE TABLE IF NOT EXISTS process_instances (
    id                  VARCHAR(36)  NOT NULL,
    payload             BYTEA        NOT NULL,
    process_id          VARCHAR(255) NOT NULL,
    version             BIGINT,
    process_version     VARCHAR(255),
    CONSTRAINT pk_process_instances PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_process_instances_process_id
    ON process_instances (process_id);

CREATE TABLE IF NOT EXISTS correlation_instances (
    id                    VARCHAR(36)  NOT NULL,
    encoded_correlation_id VARCHAR(255) NOT NULL,
    correlated_id         VARCHAR(255) NOT NULL,
    correlation           JSONB        NOT NULL,
    CONSTRAINT pk_correlation_instances PRIMARY KEY (id),
    CONSTRAINT uq_correlation_encoded UNIQUE (encoded_correlation_id)
);

CREATE INDEX IF NOT EXISTS idx_correlation_correlated_id
    ON correlation_instances (correlated_id);

-- Jobs service store: timers, SLA escalations, consent expiry.
-- Replaces the BAW Event Manager polling model with a per-service scheduler.

CREATE TABLE IF NOT EXISTS job_details (
    id                  VARCHAR(50)  NOT NULL,
    correlation_id      VARCHAR(50),
    status              VARCHAR(40),
    last_update         TIMESTAMPTZ,
    retries             INTEGER,
    execution_counter   INTEGER,
    scheduled_id        VARCHAR(40),
    priority            INTEGER,
    recipient           JSONB,
    trigger             JSONB,
    fire_time           TIMESTAMPTZ,
    execution_timeout   BIGINT,
    execution_timeout_unit VARCHAR(40),
    created             TIMESTAMPTZ,
    CONSTRAINT pk_job_details PRIMARY KEY (id)
);

CREATE INDEX IF NOT EXISTS idx_job_details_fire_time
    ON job_details (fire_time);

CREATE INDEX IF NOT EXISTS idx_job_details_status
    ON job_details (status);
