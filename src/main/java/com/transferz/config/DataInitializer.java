package com.transferz.config;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.repository.AirportRepository;
import com.transferz.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner
{
	@Autowired
	private AirportRepository airportRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Override
	public void run(String... args)
	{
		Airport schiphol = new Airport();
		schiphol.setName("Amsterdam Schiphol Airport");
		schiphol.setCode("AMS");
		schiphol.setCountry("Netherlands");
		airportRepository.save(schiphol);

		Airport heathrow = new Airport();
		heathrow.setName("London Heathrow Airport");
		heathrow.setCode("LHR");
		heathrow.setCountry("United Kingdom");
		airportRepository.save(heathrow);

		Airport cdg = new Airport();
		cdg.setName("Charles de Gaulle Airport");
		cdg.setCode("CDG");
		cdg.setCountry("France");
		airportRepository.save(cdg);

		Flight evacuationFlight = new Flight();
		evacuationFlight.setCode("EVAC-001");
		evacuationFlight.setOriginAirportCode("AMS");
		evacuationFlight.setDestinationAirportCode("LHR");
		evacuationFlight.setDepartureDateTime(LocalDateTime.now().plusHours(2));
		evacuationFlight.setArrivalDateTime(LocalDateTime.now().plusHours(3));
		evacuationFlight.setPassengerCount(0);
		flightRepository.save(evacuationFlight);
	}
}
