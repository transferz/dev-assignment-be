package com.transferz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.transferz.dao.Airport;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
public class AirportControllerTest {

    private static final String CREATE_AIRPORT_URL = "/airport/create";
    private static final String GET_PAGINATED_AIRPORT_URL = "/airport/all";
    private static final String AIRPORT_NAME = "Schiphol";
    private static final String AIRPORT_CODE = "SCH";
    private static final String AIRPORT_COUNTRY = "NL";
    private static final String PAGE_NUMBER = "0";
    private static final String PAGE_SIZE = "50";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(value = 1)
    void testCreateAirport_thenCreateSuccessfully() throws Exception {

        Airport airport = Airport.builder()
                .name(AIRPORT_NAME)
                .code(AIRPORT_CODE)
                .country(AIRPORT_COUNTRY)
                .build();

        this.mockMvc.perform(post(CREATE_AIRPORT_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(AIRPORT_NAME)))
                .andExpect(jsonPath("$.code", is(AIRPORT_CODE)))
                .andExpect(jsonPath("$.country", is(AIRPORT_COUNTRY)));
    }

    @Test
    @Order(value = 2)
    void testCreateAirport_whenNameIsInvalid_thenFailWithValidation() throws Exception {

        Airport airport = Airport.builder()
                .name(AIRPORT_NAME)
                .code(AIRPORT_CODE)
                .country(AIRPORT_COUNTRY)
                .build();

        this.mockMvc.perform(post(CREATE_AIRPORT_URL)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(airport)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Order(value = 3)
    void testGetAllPaginatedAirports_whenPaginationIsSent_thenReturnOneRecord() throws Exception {
        this.mockMvc.perform(get(GET_PAGINATED_AIRPORT_URL)
                        .param("pageNumber", PAGE_NUMBER)
                        .param("pageSize", PAGE_SIZE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.number", is(Integer.parseInt(PAGE_NUMBER))))
                .andExpect(jsonPath("$.size", is(Integer.parseInt(PAGE_SIZE))))
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].name", is(AIRPORT_NAME)))
                .andExpect(jsonPath("$.content[0].code", is(AIRPORT_CODE)))
                .andExpect(jsonPath("$.content[0].country", is(AIRPORT_COUNTRY)));
    }

    @Test
    @Order(value = 4)
    void testGetAllPaginatedAirports_whenFilterByValidCode_thenReturnOneRecord() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("code", AIRPORT_CODE);

        this.mockMvc.perform(get(GET_PAGINATED_AIRPORT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].name", is(AIRPORT_NAME)))
                .andExpect(jsonPath("$.content[0].code", is(AIRPORT_CODE)))
                .andExpect(jsonPath("$.content[0].country", is(AIRPORT_COUNTRY)));
    }

    @Test
    @Order(value = 5)
    void testGetAllPaginatedAirports_whenFilterByValidName_thenReturnOneRecord() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("name", AIRPORT_NAME);

        this.mockMvc.perform(get(GET_PAGINATED_AIRPORT_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(1)))
                .andExpect(jsonPath("$.content[0].name", is(AIRPORT_NAME)))
                .andExpect(jsonPath("$.content[0].code", is(AIRPORT_CODE)))
                .andExpect(jsonPath("$.content[0].country", is(AIRPORT_COUNTRY)));
    }

    @Test
    @Order(value = 6)
    void testGetAllPaginatedAirports_whenFilteringByInvalidCode_thenReturnEmptyResult() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("code", "anyInvalidCode");

        this.mockMvc.perform(get(GET_PAGINATED_AIRPORT_URL).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(0)));
    }

    @Test
    @Order(value = 7)
    void testGetAllPaginatedAirports_whenFilteringByInvalidName_thenReturnEmptyResult() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("name", "anyInvalidName");

        this.mockMvc.perform(get(GET_PAGINATED_AIRPORT_URL).contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(params)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(0)));
    }
}
