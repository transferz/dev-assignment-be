package com.transferz.controller;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDto;
import com.transferz.service.IAirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("airport")
public class AirportController {

  @Autowired
  private IAirportService airportService;

  @PostMapping("/create")
  public Airport create(@Valid @RequestBody AirportDto airportDto) {
    System.out.println("Creating airport with data: " + airportDto);
    return airportService.create(airportDto);
  }

  @GetMapping("/all")
  public Page<Airport> getAllPaginatedAirports(@RequestParam(defaultValue = "0", required = false) int pageNumber,
                                               @RequestParam(defaultValue = "10", required = false) int pageSize,
                                               @RequestBody(required = false) Map<String, String> parameters) {
    System.out.println("Parameters: " + parameters + ", pageNumber: " + pageNumber + ", pageSize: " + pageSize);
    return airportService.getPaginatedAirports(PageRequest.of(pageNumber, pageSize), parameters);
  }
}
