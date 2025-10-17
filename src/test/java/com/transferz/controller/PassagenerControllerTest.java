package com.transferz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferz.dao.Flight;
import com.transferz.dto.PassengerDto;
import com.transferz.enums.FlightStatus;
import com.transferz.repository.FlightRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@AutoConfigureMockMvc
public class PassagenerControllerTest {

    private static final String CREATE_PASSENGER_URL = "/passenger/create";
    private static final String PASSENGER_NAME = "Schiphol";
    private static final String FLIGHT_CODE = "FF-350";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    @Test
    void testCreatePassenger_thenBadRequest() throws Exception {

        PassengerDto dto = new PassengerDto(PASSENGER_NAME);

        this.mockMvc.perform(post(CREATE_PASSENGER_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePassenger_whenInvalidData_thenBadRequest() throws Exception {

        PassengerDto dto = new PassengerDto();

        this.mockMvc.perform(post(CREATE_PASSENGER_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreatePassenger_whenFlightIsPending_thenSuccessCreation() throws Exception {

        PassengerDto dto = new PassengerDto(PASSENGER_NAME);

        preparePendingFlight();

        this.mockMvc.perform(post(CREATE_PASSENGER_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(PASSENGER_NAME)))
                .andExpect(jsonPath("$.flightCode", is(FLIGHT_CODE)));
    }

    private void preparePendingFlight() {
        flightRepository.save(Flight.builder()
                .code(FLIGHT_CODE)
                .flightStatus(FlightStatus.PENDING)
                .destinationAirportId("any")
                .originAirportId("any")
                .passengerCount(0)
                .build());
    }


}
