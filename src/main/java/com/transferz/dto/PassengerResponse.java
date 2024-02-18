package com.transferz.dto;

import lombok.Data;

@Data
public class PassengerResponse {
    private String name;
    private FlightDetails flightDetails;
}
