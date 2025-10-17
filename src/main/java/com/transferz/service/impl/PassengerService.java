package com.transferz.service.impl;


import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDto;
import com.transferz.enums.FlightStatus;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.service.IPassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PassengerService implements IPassengerService {

  private int amountOfPassengerPerFlight = 150;

  @Autowired
  private PassengerRepository passengerRepository;

  @Autowired
  private FlightRepository flightRepository;


  @Override
  public Passenger create(PassengerDto dto) {
    Flight flight = getActiveFlight();
    Passenger passenger = new Passenger();

    if (passengerRepository.getAllPassengersNamesPerFlight(flight.getCode())
        .contains(dto.getName())) {
      throw new RuntimeException("User is already registered for this flight");
    }

    flight.setPassengerCount(flight.getPassengerCount() + 1);
    flightRepository.save(flight);

    passenger.setName(dto.getName());
    passenger.setFlightCode(flight.getCode());

    return passengerRepository.save(passenger);
  }

  private Flight getActiveFlight() {
    Optional<Flight> availableFlight = flightRepository.getFirstFlightByStatus(FlightStatus.AVAILABLE.name());

    if (availableFlight.isPresent()) {
      if (availableFlight.get().getPassengerCount() < amountOfPassengerPerFlight) {
        return availableFlight.get();
      } else {
        updateFlightStatus(availableFlight.get(), FlightStatus.UNAVAILABLE);
      }
    }

    Optional<Flight> pendingFlight = flightRepository.getFirstFlightByStatus(FlightStatus.PENDING.name());

    if (pendingFlight.isPresent() && pendingFlight.get().getPassengerCount() < amountOfPassengerPerFlight) {
      return updateFlightStatus(pendingFlight.get(), FlightStatus.AVAILABLE);
    }

    throw new RuntimeException("Flight not found!");
  }

  private Flight updateFlightStatus(Flight flight, FlightStatus status) {
    flight.setFlightStatus(status);
    return flightRepository.save(flight);
  }
}
