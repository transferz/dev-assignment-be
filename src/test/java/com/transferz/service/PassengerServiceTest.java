package com.transferz.service;

import com.transferz.dao.Flight;
import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDto;
import com.transferz.enums.FlightStatus;
import com.transferz.repository.FlightRepository;
import com.transferz.repository.PassengerRepository;
import com.transferz.service.impl.PassengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    private static final String PASSENGER_NAME = "John Smith";
    private static final String FLIGHT_CODE = "F-365";
    private static final int FLIGHT_SIZE = 5;
    private static final int FLIGHT_PASSENGER_COUNTER_EMPTY = 0;
    private static final int FLIGHT_PASSENGER_COUNTER_FULL = 5;

    @InjectMocks
    private PassengerService underTest;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private FlightRepository flightRepository;

    private PassengerDto passengerDto;
    private Flight activeFlight;
    private Flight fullActiveFlight;
    private Flight pendingFlight;
    private Passenger passenger;

    @BeforeEach
    public void setup() {
        ReflectionTestUtils.setField(underTest, "amountOfPassengerPerFlight", FLIGHT_SIZE);

        activeFlight = Flight.builder()
                .passengerCount(FLIGHT_PASSENGER_COUNTER_EMPTY)
                .flightStatus(FlightStatus.AVAILABLE)
                .code(FLIGHT_CODE)
                .build();

        fullActiveFlight = Flight.builder()
                .passengerCount(FLIGHT_PASSENGER_COUNTER_FULL)
                .flightStatus(FlightStatus.AVAILABLE)
                .code(FLIGHT_CODE)
                .build();

        pendingFlight = Flight.builder()
                .passengerCount(FLIGHT_PASSENGER_COUNTER_EMPTY)
                .flightStatus(FlightStatus.PENDING)
                .code(FLIGHT_CODE)
                .build();

        passengerDto = new PassengerDto(PASSENGER_NAME);

        passenger = Passenger.builder()
                .name(PASSENGER_NAME)
                .flightCode(FLIGHT_CODE)
                .build();
    }

    @Test
    public void testCreate_whenHasActiveFlightAndFreePlace_thenSuccessCreation() {

        when(flightRepository.getFirstFlightByStatus(eq(FlightStatus.AVAILABLE.name()))).thenReturn(Optional.of(activeFlight));
        when(passengerRepository.getAllPassengersNamesPerFlight(eq(FLIGHT_CODE))).thenReturn(Collections.emptyList());
        when(flightRepository.save(any())).thenReturn(activeFlight);
        when(passengerRepository.save(any())).thenReturn(passenger);

        // WHEN:
        underTest.create(passengerDto);

        // THEN:
        verify(flightRepository, times(1)).getFirstFlightByStatus(eq(FlightStatus.AVAILABLE.name()));
        verify(passengerRepository, times(1)).getAllPassengersNamesPerFlight(eq(FLIGHT_CODE));
        verify(flightRepository, times(1)).save(any());
        verify(passengerRepository, times(1)).save(any());
    }

    @Test
    public void testCreate_whenHasActiveFlightButNoFreePlaceAndPendingFlight_thenSuccessCreation() {

        when(flightRepository.getFirstFlightByStatus(eq(FlightStatus.AVAILABLE.name()))).thenReturn(Optional.of(fullActiveFlight));
        when(flightRepository.save(any())).thenReturn(pendingFlight);
        when(flightRepository.getFirstFlightByStatus(eq(FlightStatus.PENDING.name()))).thenReturn(Optional.of(pendingFlight));
        when(passengerRepository.getAllPassengersNamesPerFlight(eq(FLIGHT_CODE))).thenReturn(Collections.emptyList());
        when(passengerRepository.save(any())).thenReturn(passenger);

        // WHEN:
        underTest.create(passengerDto);

        // THEN:
        verify(flightRepository, times(1)).getFirstFlightByStatus(eq(FlightStatus.AVAILABLE.name()));
        verify(flightRepository, times(1)).getFirstFlightByStatus(eq(FlightStatus.PENDING.name()));
        verify(passengerRepository, times(1)).getAllPassengersNamesPerFlight(eq(FLIGHT_CODE));
        verify(flightRepository, times(3)).save(any());
        verify(passengerRepository, times(1)).save(any());
    }

    @Test
    public void testCreate_whenHasNoFlightsAvailable_thenThrowException() {
        when(flightRepository.getFirstFlightByStatus(eq(FlightStatus.AVAILABLE.name()))).thenReturn(Optional.empty());
        when(flightRepository.getFirstFlightByStatus(eq(FlightStatus.PENDING.name()))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            underTest.create(passengerDto);
        });
    }

    @Test
    public void testCreate_whenHasActiveFlightAndButAlreadyRegistered_thenThrowException() {
        when(flightRepository.getFirstFlightByStatus(eq(FlightStatus.AVAILABLE.name()))).thenReturn(Optional.of(activeFlight));
        when(passengerRepository.getAllPassengersNamesPerFlight(eq(FLIGHT_CODE))).thenReturn(Collections.singletonList(PASSENGER_NAME));

        assertThrows(RuntimeException.class, () -> {
            underTest.create(passengerDto);
        });
    }

}
