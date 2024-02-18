package com.transferz.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String flightCode;
    @ManyToOne
    @JoinTable(name = "flight_passenger",
            joinColumns = @JoinColumn(name = "id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "flightId", referencedColumnName = "flightId"))
    private Flight flight;
}
