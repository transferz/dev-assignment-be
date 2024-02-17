package com.transferz.dao;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AirportTest {

    @Test
    void testAirportConstructor() {
        Airport airport = new Airport();
        airport.setCode("code");
        airport.setName("name");
        airport.setCountry("country");

        assertThat(airport.getCode()).isEqualTo("code");
        assertThat(airport.getName()).isEqualTo("name");
        assertThat(airport.getCountry()).isEqualTo("country");
    }

}