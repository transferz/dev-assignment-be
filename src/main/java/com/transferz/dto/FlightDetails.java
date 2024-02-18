package com.transferz.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightDetails {
    private String code;
    private String originAirportId;
    private String destinationAirportId;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}

