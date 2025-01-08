CREATE TABLE ticket_service_tickets (
    id SERIAL,
    title TEXT NOT NULL,
    description TEXT,
    status TEXT,
    priority TEXT,
    user_id SERIAL NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id, created_at)
);

SELECT create_hypertable('ticket_service_tickets', 'created_at', if_not_exists => TRUE);


