package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDto;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService
{
	@Autowired
	private PassengerRepository passengerRepository;

	@Autowired
	private FlightRepository flightRepository;

	@Autowired
	private AirportService airportService;

	@Value("${flight.max.passengers}")
	private Integer maxPassengers;

	public Passenger addPassenger(PassengerDto passengerDto)
	{
		Optional<Flight> flightOptional = flightRepository.findByCode(passengerDto.getFlightCode());
		if (!flightOptional.isPresent())
		{
			throw new IllegalArgumentException("Flight with code " + passengerDto.getFlightCode() + " does not exist");
		}

		Flight flight = flightOptional.get();

		Optional<Passenger> existingPassenger = passengerRepository.findByNameAndFlightCode(
			passengerDto.getName(), passengerDto.getFlightCode());
		if (existingPassenger.isPresent())
		{
			throw new IllegalArgumentException("Passenger with name " + passengerDto.getName() + " already exists on flight " + passengerDto.getFlightCode());
		}

		List<Passenger> allPassengers = passengerRepository.findByFlightCode(passengerDto.getFlightCode());
		int currentPassengerCount = allPassengers.size();

		if (currentPassengerCount >= maxPassengers)
		{
			flight.setDepartureDateTime(LocalDateTime.now());
			flightRepository.save(flight);

			String newFlightCode = "FL-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
			Flight newFlight = new Flight();
			newFlight.setCode(newFlightCode);
			newFlight.setOriginAirportCode(flight.getOriginAirportCode());

			Airport destination = airportService.getRandomAvailableAirport();
			newFlight.setDestinationAirportCode(destination.getCode());
			newFlight.setDepartureDateTime(LocalDateTime.now().plusHours(2));
			newFlight.setArrivalDateTime(LocalDateTime.now().plusHours(4));
			newFlight.setPassengerCount(0);

			flightRepository.save(newFlight);

			passengerDto.setFlightCode(newFlightCode);
		}

		Passenger passenger = new Passenger();
		passenger.setName(passengerDto.getName());
		passenger.setFlightCode(passengerDto.getFlightCode());

		Passenger savedPassenger = passengerRepository.save(passenger);

		flight.setPassengerCount(currentPassengerCount + 1);
		flightRepository.save(flight);

		return savedPassenger;
	}
}
