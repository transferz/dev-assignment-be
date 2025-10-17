package com.transferz.controller;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDto;
import com.transferz.service.AirportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/airports")
public class AirportController
{
	@Autowired
	private AirportService airportService;

	@GetMapping
	public ResponseEntity<Page<Airport>> getAllAirports(
		@RequestParam(required = false) String filter,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		Page<Airport> airports = airportService.getAllAirports(filter, pageable);
		return ResponseEntity.ok(airports);
	}

	@PostMapping
	public ResponseEntity<Airport> addAirport(@Valid @RequestBody AirportDto airportDto)
	{
		try
		{
			Airport airport = airportService.addAirport(airportDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(airport);
		}
		catch (IllegalArgumentException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
