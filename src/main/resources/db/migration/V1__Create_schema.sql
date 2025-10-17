CREATE SEQUENCE hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE airports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(20) NOT NULL,
    country VARCHAR(60) NOT NULL
);

CREATE TABLE flights (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL,
    origin_airport_code VARCHAR(20) NOT NULL,
    destination_airport_code VARCHAR(20) NOT NULL,
    departure_date_time TIMESTAMP NOT NULL,
    arrival_date_time TIMESTAMP NOT NULL,
    passenger_count INTEGER NOT NULL
);

CREATE TABLE passengers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(1024) NOT NULL,
    flight_code VARCHAR(20) NOT NULL
);
