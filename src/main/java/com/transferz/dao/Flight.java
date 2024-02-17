package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "flights")
public class Flight {
	@Id
	@GeneratedValue
	private UUID flightId;

	@Column(nullable = false)
	private String code;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "origin_airport_id", nullable = false)
	private Airport originAirport;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "destination_airport_id", nullable = false)
	private Airport destinationAirport;

	@Column(name = "departure_time", nullable = false)
	private LocalDateTime departureTime;

	@Column(name = "arrival_time", nullable = false)
	private LocalDateTime arrivalTime;
}