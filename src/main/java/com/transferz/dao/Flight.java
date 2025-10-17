package com.transferz.dao;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "flights")
@Data
public class Flight
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 20)
	private String code;

	@Column(nullable = false, length = 20)
	private String originAirportCode;

	@Column(nullable = false, length = 20)
	private String destinationAirportCode;

	@Column(nullable = false)
	private LocalDateTime departureDateTime;

	@Column(nullable = false)
	private LocalDateTime arrivalDateTime;

	@Column(nullable = false)
	private Integer passengerCount = 0;
	
}
