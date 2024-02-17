package com.transferz.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerTest {
    @Test
    void testPassenger() {
        Passenger passenger = new Passenger();
        passenger.setName("Tom :D");
        passenger.setFlight(new Flight());

        assertThat(passenger.getName()).isEqualTo("Tom :D");
        assertThat(passenger.getFlight()).isInstanceOf(Flight.class);
    }

}