package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dto.AddNewAirportRequest;
import com.transferz.dto.AddNewPassengerRequest;
import com.transferz.dto.GenericSuccessResponse;
import com.transferz.exception.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EvacuationService {
    Page<Airport> getAllAirports(String name, String code, Pageable pageable) throws DatabaseException;

    GenericSuccessResponse addNewAirport(AddNewAirportRequest addNewAirportRequest) throws AirportAlreadyExistException;

    GenericSuccessResponse addNewPassenger(AddNewPassengerRequest addNewPassengerRequest) throws AirportNotFoundException, PassengerAlreadyExistException, PassengerNotFoundException, FlightNotFoundException;
}
