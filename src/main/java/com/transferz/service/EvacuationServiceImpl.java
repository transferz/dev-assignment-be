package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.*;
import com.transferz.exception.*;
import com.transferz.repository.AirportRepository;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.util.ApiConstants;
import com.transferz.util.ValidationWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class EvacuationServiceImpl implements EvacuationService {

    private final AirportRepository airportRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;
    private final ModelMapper modelMapper;
    private final ValidationWrapper validationWrapper;
    @Value("${flight.capacity}")
    private int capacity;

    private static void checkPassengerIfAlreadyPresent(AddNewPassengerRequest addNewPassengerRequest, List<Passenger> passengersOfFlight) throws PassengerAlreadyExistException {
        if (passengersOfFlight.stream().anyMatch(passenger -> passenger.getName().equals(addNewPassengerRequest.getName()))) {
            throw new PassengerAlreadyExistException(ApiConstants.PASSENGER_EXISTS_EXCEPTION);
        }
    }

    public Page<Airport> getAllAirports(String name, String code, Pageable pageable) throws DatabaseException {
        log.info("List of customers returned");
        try {
            if (name != null && code != null) {
                return airportRepository.findByNameAndCodeContainingIgnoreCase(name, code, pageable);
            } else if (name != null) {
                return airportRepository.findByNameContainingIgnoreCase(name, pageable);
            } else if (code != null) {
                return airportRepository.findByCodeContainingIgnoreCase(code, pageable);
            } else {
                return airportRepository.findAll(pageable);
            }
        } catch (Exception e) {
            throw new DatabaseException(ApiConstants.DATABASE_EXCEPTION);
        }
    }

    public GenericSuccessResponse addNewAirport(AddNewAirportRequest addNewAirportRequest) {
        log.info("Created airport : {} with ID: {} ", addNewAirportRequest.getName(), addNewAirportRequest.getCode());
        Airport airport = modelMapper.map(addNewAirportRequest, Airport.class);
        airportRepository.save(airport);
        return validationWrapper.mapSuccessResponse(airport, ApiConstants.SUCCESS_RESPONSE);
    }

    public GenericSuccessResponse addNewPassenger(AddNewPassengerRequest addNewPassengerRequest) throws AirportNotFoundException, PassengerAlreadyExistException, PassengerNotFoundException, FlightNotFoundException {
        var firstAvailableFlight = flightRepository.findFirstAvailableFlight()
                .orElseThrow(() -> new FlightNotFoundException(ApiConstants.FLIGHT_EXCEPTION));
        List<Passenger> passengersOfFlight = passengerRepository.findAllByFlightCode(firstAvailableFlight.getCode())
                .orElseThrow(() -> new PassengerNotFoundException(ApiConstants.PASSENGER_EXCEPTION));

        checkPassengerIfAlreadyPresent(addNewPassengerRequest, passengersOfFlight);
        Passenger passenger = mapFlightToPassenger(addNewPassengerRequest, firstAvailableFlight);
        checkIfFlightCapacityIsFull(passengersOfFlight, firstAvailableFlight);
        flightRepository.save(firstAvailableFlight);
        log.info("Added passenger : {} ", addNewPassengerRequest.getName());
        PassengerResponse passengerResponse = mapPassengerResponse(passenger);
        return validationWrapper.mapSuccessResponse(passengerResponse, ApiConstants.SUCCESS_RESPONSE);

    }

    private PassengerResponse mapPassengerResponse(Passenger passenger) {
        FlightDetails flightResponse = modelMapper.map(passenger.getFlight(), FlightDetails.class);
        PassengerResponse passengerResponse = new PassengerResponse();
        passengerResponse.setName(passenger.getName());
        passengerResponse.setFlightDetails(flightResponse);
        return passengerResponse;
    }

    private void checkIfFlightCapacityIsFull(List<Passenger> passengersOfFlight, Flight firstAvailableFlight) throws AirportNotFoundException {
        if (passengersOfFlight.size() == capacity) {
            departFlight(firstAvailableFlight);
        }
    }

    private void departFlight(Flight firstAvailableFlight) throws AirportNotFoundException {
        String destinationAirportId = airportRepository.findAvailableAirportCode()
                .orElseThrow(() -> new AirportNotFoundException(ApiConstants.AIRPORT_EXCEPTION));
        firstAvailableFlight.setIsFullCapacity(true);
        firstAvailableFlight.setDestinationAirportId(destinationAirportId);
        log.info("Flight {} departed", firstAvailableFlight.getCode());
    }

    private Passenger mapFlightToPassenger(AddNewPassengerRequest addNewPassengerRequest, Flight firstAvailableFlight) {
        var passenger = modelMapper.map(addNewPassengerRequest, Passenger.class);
        passenger.setFlightCode(firstAvailableFlight.getCode());
        passenger.setFlight(firstAvailableFlight);
        passengerRepository.save(passenger);
        return passenger;
    }

}
