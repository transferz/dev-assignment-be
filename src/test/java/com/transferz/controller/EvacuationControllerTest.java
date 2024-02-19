package com.transferz.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
class EvacuationControllerTest extends IntegrationTest {

    @Test
    void getAllAirports() throws Exception {
        mvc.perform((get("/evacuate/nl/airports")
                        .contentType(MediaType.APPLICATION_JSON)
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("LHR")));
    }


    @Test
    void addAirportSuccess() throws Exception {
        mvc.perform((post("/evacuate/nl/airport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Kozhikode\", \"code\": \"CCJ\", \"country\": \"India\"}")
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Kozhikode")));
    }

    @Test
    void addAirportException() throws Exception {
        mvc.perform((post("/evacuate/nl/airport")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", \"code\": \"CCJ\", \"country\": \"India\"}")
                ))
                .andExpect(status().isBadRequest());
    }

    @Test
    void addAirportInternalErrorException() throws Exception {
        mvc.perform((post("/evacuate/nl/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"A\", \"code\": \"CCJ\", \"country\": \"India\"}")
                ))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void addPassengerSuccess() throws Exception {
        mvc.perform((post("/evacuate/nl/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Neethu\"}")
                ))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Neethu")));
    }

    @Test
    void addPassengerException() throws Exception {
        addPassengerSuccess();
        mvc.perform((post("/evacuate/nl/passenger")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Neethu\"}")
                ))
                .andExpect(status().isConflict());
    }
}
