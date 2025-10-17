package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface IAirportService {

  Airport create(AirportDto dto);

  Page<Airport> getPaginatedAirports(Pageable pageable, Map<String, String> parameters);

}
