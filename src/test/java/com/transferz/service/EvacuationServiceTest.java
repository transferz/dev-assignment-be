package com.transferz.service;

import com.transferz.dao.Airport;
import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.AddNewAirportRequest;
import com.transferz.dto.AddNewPassengerRequest;
import com.transferz.dto.GenericSuccessResponse;
import com.transferz.exception.*;
import com.transferz.repository.AirportRepository;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.util.ValidationWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EvacuationServiceTest {
    @Mock
    private AirportRepository airportRepository;
    @Mock
    private PassengerRepository passengerRepository;
    @Mock
    private FlightRepository flightRepository;
    @Mock
    private ValidationWrapper validationWrapper;
    @Mock
    private ModelMapper modelMapper;
    @InjectMocks
    private EvacuationServiceImpl evacuationService;
    @Value("${flight.capacity}")
    private int capacity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllAirports() throws Exception {
        Page<Airport> mockPage = mock(Page.class);
        when(airportRepository.findAll(any(Pageable.class))).thenReturn(mockPage);

        Page<Airport> result = evacuationService.getAllAirports(null, null, Pageable.unpaged());

        assertNotNull(result);
        verify(airportRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testGetAllAirportsException() {
        when(airportRepository.findAll(any(Pageable.class))).thenThrow(NullPointerException.class);

        assertThrows(DatabaseException.class, () -> evacuationService.getAllAirports(null, null, Pageable.unpaged()));

        verify(airportRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testAddNewAirport() throws AirportAlreadyExistException {
        AddNewAirportRequest request = new AddNewAirportRequest();
        request.setName("Test Airport");
        request.setCode("TEST");
        request.setCountry("Test Country");

        when(modelMapper.map(any(AddNewAirportRequest.class), any())).thenReturn(new Airport());
        when(airportRepository.save(any(Airport.class))).thenReturn(new Airport());
        when(validationWrapper.mapSuccessResponse(any(), any())).thenReturn(new GenericSuccessResponse());

        assert (evacuationService.addNewAirport(request).getClass()).equals(GenericSuccessResponse.class);
        verify(airportRepository, times(1)).save(any(Airport.class));
    }

    @Test
    void testAirportAlreadyExists() {
        AddNewAirportRequest request = new AddNewAirportRequest();
        request.setName("Test Airport");
        request.setCode("TST");
        request.setCountry("Test Country");
        Airport airport = new Airport();
        airport.setName("Test Airport");
        airport.setCode("TST");
        airport.setCountry("Test Country");
        List<Airport> airportList = new ArrayList<>();
        airportList.add(airport);

        when(modelMapper.map(any(AddNewAirportRequest.class), any())).thenReturn(new Airport());
        when(airportRepository.findAll()).thenReturn(airportList);

        assertThrows(AirportAlreadyExistException.class, () -> evacuationService.addNewAirport(request));
        verify(airportRepository, times(0)).save(any(Airport.class));
    }

    @Test
    void testAddNewPassenger() throws Exception {
        AddNewPassengerRequest request = new AddNewPassengerRequest();
        request.setName("John");
        Passenger passenger = new Passenger();
        passenger.setName("Alex");
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        when(modelMapper.map(any(AddNewPassengerRequest.class), any())).thenReturn(new Passenger());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(new Passenger());
        when(passengerRepository.findAllByFlightCode(any())).thenReturn(Optional.of(passengers));
        when(airportRepository.findAvailableAirportCode()).thenReturn(Optional.of("AMS"));
        when(flightRepository.findFirstAvailableFlight()).thenReturn(Optional.of(new Flight()));
        when(flightRepository.save(any(Flight.class))).thenReturn(new Flight());
        when(validationWrapper.mapSuccessResponse(any(), any())).thenReturn(new GenericSuccessResponse());

        assert (evacuationService.addNewPassenger(request).getClass()).equals(GenericSuccessResponse.class);
        verify(passengerRepository, times(1)).save(any(Passenger.class));
    }

    @Test
    void testFullCapacityFlightDeparture() throws Exception {
        AddNewPassengerRequest request = new AddNewPassengerRequest();
        request.setName("John");
        Passenger passenger = new Passenger();
        passenger.setName("Alex");
        List<Passenger> passengers = mock(List.class);
        passengers.add(passenger);

        when(modelMapper.map(any(AddNewPassengerRequest.class), any())).thenReturn(new Passenger());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(new Passenger());
        when(passengerRepository.findAllByFlightCode(any())).thenReturn(Optional.of(passengers));
        when(airportRepository.findAvailableAirportCode()).thenReturn(Optional.of("AMS"));
        when(flightRepository.findFirstAvailableFlight()).thenReturn(Optional.of(new Flight()));
        when(flightRepository.save(any(Flight.class))).thenReturn(new Flight());
        when(passengers.size()).thenReturn(capacity);
        when(validationWrapper.mapSuccessResponse(any(), any())).thenReturn(new GenericSuccessResponse());

        assert (evacuationService.addNewPassenger(request).getClass()).equals(GenericSuccessResponse.class);
        verify(passengerRepository, times(1)).save(any(Passenger.class));
    }

    @Test
    void testAirportNotFoundException() {
        AddNewPassengerRequest request = new AddNewPassengerRequest();
        request.setName("John");
        Passenger passenger = new Passenger();
        passenger.setName("Alex");
        List<Passenger> passengers = mock(List.class);
        passengers.add(passenger);

        when(modelMapper.map(any(AddNewPassengerRequest.class), any())).thenReturn(new Passenger());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(new Passenger());
        when(passengerRepository.findAllByFlightCode(any())).thenReturn(Optional.of(passengers));
        when(airportRepository.findAvailableAirportCode()).thenReturn(Optional.empty());
        when(flightRepository.findFirstAvailableFlight()).thenReturn(Optional.of(new Flight()));

        assertThrows(AirportNotFoundException.class, () -> evacuationService.addNewPassenger(request));
        verify(flightRepository, times(0)).save(any(Flight.class));
    }

    @Test
    void testPassengerAlreadyExistException() {
        AddNewPassengerRequest request = new AddNewPassengerRequest();
        request.setName("John");
        Passenger passenger = new Passenger();
        passenger.setName("John");
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(passenger);

        when(modelMapper.map(any(AddNewPassengerRequest.class), any())).thenReturn(new Passenger());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(new Passenger());
        when(passengerRepository.findAllByFlightCode(any())).thenReturn(Optional.of(passengers));
        when(flightRepository.findFirstAvailableFlight()).thenReturn(Optional.of(new Flight()));

        assertThrows(PassengerAlreadyExistException.class, () -> evacuationService.addNewPassenger(request));
        verify(passengerRepository, times(0)).save(any(Passenger.class));
    }

    @Test
    void testPassengerNotFoundException() {
        AddNewPassengerRequest request = new AddNewPassengerRequest();
        request.setName("John");

        when(modelMapper.map(any(AddNewPassengerRequest.class), any())).thenReturn(new Passenger());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(new Passenger());
        when(flightRepository.findFirstAvailableFlight()).thenReturn(Optional.of(new Flight()));

        assertThrows(PassengerNotFoundException.class, () -> evacuationService.addNewPassenger(request));
        verify(passengerRepository, times(0)).save(any(Passenger.class));
    }

    @Test
    void testFlightNotFoundException() {
        AddNewPassengerRequest request = new AddNewPassengerRequest();
        request.setName("John");

        when(modelMapper.map(any(AddNewPassengerRequest.class), any())).thenReturn(new Passenger());
        when(passengerRepository.save(any(Passenger.class))).thenReturn(new Passenger());

        assertThrows(FlightNotFoundException.class, () -> evacuationService.addNewPassenger(request));
        verify(passengerRepository, times(0)).save(any(Passenger.class));
    }
}
