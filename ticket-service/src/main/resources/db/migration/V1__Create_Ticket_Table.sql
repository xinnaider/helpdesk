CREATE TABLE tickets (
    id SERIAL,
    title TEXT NOT NULL,
    description TEXT,
    status TEXT,
    priority TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id, created_at)
);

SELECT create_hypertable('tickets', 'created_at', if_not_exists => TRUE);


