package com.transferz.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID flightId;
    private String code;
    private String originAirportId;
    private String destinationAirportId;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime departureTime;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime arrivalTime;
    private Boolean isFullCapacity;
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL)
    private List<Passenger> passengerList;
}

