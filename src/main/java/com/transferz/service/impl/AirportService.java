package com.transferz.service.impl;


import com.transferz.dao.Airport;
import com.transferz.dto.AirportDto;
import com.transferz.repository.AirportRepository;
import com.transferz.repository.specification.AirportSpecification;
import com.transferz.service.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AirportService implements IAirportService {

  @Autowired
  private AirportRepository airportRepository;

  @Override
  public Airport create(AirportDto dto) {
    return airportRepository.save(new Airport(dto));
  }

  @Override
  public Page<Airport> getPaginatedAirports(Pageable pageable, Map<String, String> parameters) {
    return airportRepository.findAll(AirportSpecification.filterAirport(parameters), pageable);
  }
}
