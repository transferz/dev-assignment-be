CREATE TABLE airport (
  airport_id UUID NOT NULL,
   name VARCHAR(255),
   code VARCHAR(255),
   country VARCHAR(255),
   CONSTRAINT pk_airport PRIMARY KEY (airport_id)
);

ALTER TABLE airport ADD CONSTRAINT uc_airport_code UNIQUE (code);
ALTER TABLE airport ADD CONSTRAINT uc_airport_name UNIQUE (name);

CREATE TABLE flight (
  flight_id UUID NOT NULL,
   code VARCHAR(255),
   origin_airport_id VARCHAR(255),
   destination_airport_id VARCHAR(255),
   departure_time TIMESTAMP,
   arrival_time TIMESTAMP,
   is_full_capacity BOOLEAN,
   CONSTRAINT pk_flight PRIMARY KEY (flight_id)
);