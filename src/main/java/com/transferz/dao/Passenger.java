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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "passenger_id")
	private UUID passengerId;

	@Column(nullable = false)
	private String name;

	@ManyToOne
	@JoinColumn(name = "flight_code", referencedColumnName = "code", nullable = false)
	private Flight flight;
}