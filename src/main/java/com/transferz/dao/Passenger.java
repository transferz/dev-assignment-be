package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passengers")
public class Passenger {
	@Id
	@Column(name = "passenger_id")
	private UUID passengerId;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "flight_code", referencedColumnName = "code", nullable = false)
	private Flight flight;

	public Passenger(String name, Flight flight) {
		this.passengerId = UUID.randomUUID();
		this.name = name;
		this.flight = flight;
	}
}