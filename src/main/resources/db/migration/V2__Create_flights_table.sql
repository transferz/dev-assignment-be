CREATE TABLE flights (
    code                     VARCHAR(20) PRIMARY KEY,
    origin_airport_code      VARCHAR(20)                 NOT NULL,
    destination_airport_code VARCHAR(20)                 NOT NULL,
    departure_time           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    arrival_time             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    FOREIGN KEY (origin_airport_code) REFERENCES airports (code),
    FOREIGN KEY (destination_airport_code) REFERENCES airports (code)
);