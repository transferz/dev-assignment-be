package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDto;
import com.transferz.repository.AirportRepository;
import com.transferz.service.impl.AirportService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceTest {

    @InjectMocks
    private AirportService underTest;

    @Mock
    private AirportRepository airportRepository;

    @Test
    public void testCreate() {
        Airport expectedAirport = new Airport();
        when(airportRepository.save(any())).thenReturn(expectedAirport);

        Airport result = underTest.create(new AirportDto());

        assertEquals(expectedAirport, result);
    }

    @Test
    public void testGetAllWithSpecificationAndPagination() {
        Page<Airport> expectedPage = new PageImpl<>(Collections.emptyList());
        when(airportRepository.findAll((Specification<Airport>) any(), (Pageable) any())).thenReturn(expectedPage);

        Page result = underTest.getPaginatedAirports(PageRequest.of(0, 10), new HashMap());

        assertEquals(expectedPage, result);
    }

}
