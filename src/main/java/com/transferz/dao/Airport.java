package com.transferz.dao;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID airportId;
    @Column(unique = true)
    private String name;
    @Column(unique = true)
    private String code;
    private String country;

}
