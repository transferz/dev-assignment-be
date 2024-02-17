CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE passengers (
    passenger_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name         VARCHAR(1024) NOT NULL,
    flight_id    UUID          NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights (flight_id)
);