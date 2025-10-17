package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dto.AirportDto;
import com.transferz.repository.AirportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService
{
	@Autowired
	private AirportRepository airportRepository;

	public Page<Airport> getAllAirports(String filter, Pageable pageable)
	{
		if (filter != null && !filter.isEmpty())
		{
			return airportRepository.findByNameContainingOrCodeContaining(filter, filter, pageable);
		}
		return airportRepository.findAll(pageable);
	}

	public Airport addAirport(AirportDto airportDto)
	{
		Optional<Airport> existingByCode = airportRepository.findByCode(airportDto.getCode());
		if (existingByCode.isPresent())
		{
			throw new IllegalArgumentException("Airport with code " + airportDto.getCode() + " already exists");
		}

		Optional<Airport> existingByName = airportRepository.findByName(airportDto.getName());
		if (existingByName.isPresent())
		{
			throw new IllegalArgumentException("Airport with name " + airportDto.getName() + " already exists");
		}

		Airport airport = new Airport();
		airport.setName(airportDto.getName());
		airport.setCode(airportDto.getCode());
		airport.setCountry(airportDto.getCountry());

		return airportRepository.save(airport);
	}

	public Airport getRandomAvailableAirport()
	{
		return airportRepository.findAll().stream()
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("No airports available"));
	}
}
