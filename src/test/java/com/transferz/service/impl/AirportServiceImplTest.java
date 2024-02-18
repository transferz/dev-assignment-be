package com.transferz.service.impl;

import com.transferz.dao.Airport;
import com.transferz.repository.AirportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AirportServiceImplTest {

    @Mock
    private AirportRepository airportRepository;

    private AirportServiceImpl airportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        airportService = new AirportServiceImpl(airportRepository);
    }

    @Test
    void findAllAirports_returns_page_size_of_airports() {
        Airport airport1 = new Airport();
        Airport airport2 = new Airport();

        List<Airport> airports = Arrays.asList(airport1, airport2);
        Page<Airport> airportPage = new PageImpl<>(airports);
        Pageable pageable = PageRequest.of(0, 10);

        when(airportRepository.findAll(pageable)).thenReturn(airportPage);

        Page<Airport> result = airportService.findAllAirports(pageable);
        assertEquals(airportPage, result);
    }

    @Test
    void addAirport_adds_airport_to_repository() {
        Airport airport = new Airport();

        when(airportRepository.save(airport)).thenReturn(airport);

        Airport result = airportService.addAirport(airport);
        assertEquals(airport, result);
    }

    @Test
    void findAllAirports_noAirports_returns_empty_page() {
        Pageable pageable = PageRequest.of(0, 10);
        when(airportRepository.findAll(pageable)).thenReturn(Page.empty());

        Page<Airport> result = airportService.findAllAirports(pageable);
        assertEquals(Page.empty(), result);
    }

    @Test
    void addAirport_unsuccessfulSave() {
        Airport airport = new Airport();
        when(airportRepository.save(airport)).thenReturn(null);
        Airport result = airportService.addAirport(airport);
        assertNull(result);
    }

    @Test
    void findAllAirports_invalidPageable() {
        assertThrows(IllegalArgumentException.class, () -> airportService.findAllAirports(PageRequest.of(-1, -1)));
    }
}