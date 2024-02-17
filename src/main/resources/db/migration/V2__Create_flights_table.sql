CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE flights (
    flight_id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code                   VARCHAR(20) NOT NULL UNIQUE,
    origin_airport_id      UUID        NOT NULL,
    destination_airport_id UUID        NOT NULL,
    departure_date_time    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arrival_date_time      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (origin_airport_id) REFERENCES airports (airport_id),
    FOREIGN KEY (destination_airport_id) REFERENCES airports (airport_id)
);