package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "flights")
public class Flight {

	@Id
	@Column(name = "flight_id")
	private UUID flightId;

	@Column(nullable = false)
	private String code;

	@ManyToOne
	@JoinColumn(name = "origin_airport_code", referencedColumnName = "code", nullable = false)
	private Airport originAirport;

	@ManyToOne
	@JoinColumn(name = "destination_airport_code", referencedColumnName = "code", nullable = false)
	private Airport destinationAirport;

	@Column(name = "departure_time", nullable = false)
	private LocalDateTime departureTime;

	@Column(name = "arrival_time", nullable = false)
	private LocalDateTime arrivalTime;

	public Flight(String code, Airport originAirport, Airport destinationAirport, LocalDateTime departureTime, LocalDateTime arrivalTime) {
		this.flightId = UUID.randomUUID();
		this.code = code;
		this.originAirport = originAirport;
		this.destinationAirport = destinationAirport;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}
}