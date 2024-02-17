CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE TABLE airports (
    airport_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name       VARCHAR(255) NOT NULL UNIQUE,
    code       VARCHAR(20)  NOT NULL UNIQUE,
    country    VARCHAR(60)  NOT NULL
);