package com.transferz.dao;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class FlightTest {
    @Test
    void testFlightConstructor() {
        String code = "code";
        Flight flight = new Flight();
        Airport origin = new Airport();
        Airport destination = new Airport();
        LocalDateTime departureTime = LocalDateTime.now();
        LocalDateTime arrivalTime = LocalDateTime.now();

        flight.setCode(code);
        flight.setOriginAirport(origin);
        flight.setDestinationAirport(destination);
        flight.setDepartureTime(departureTime);
        flight.setArrivalTime(arrivalTime);

        assertThat(flight.getCode()).isEqualTo(code);
        assertThat(flight.getOriginAirport()).isEqualTo(origin);
        assertThat(flight.getDestinationAirport()).isEqualTo(destination);
        assertThat(flight.getDepartureTime()).isEqualTo(departureTime);
        assertThat(flight.getArrivalTime()).isEqualTo(arrivalTime);

    }

}