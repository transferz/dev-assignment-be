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
@Table(name = "airports")
public class Airport {
	@Id
	@Column(name = "airport_id")
	private UUID airportId;

	@Column(unique = true, nullable = false)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String country;

	public Airport(String code, String name, String country) {
		this.airportId = UUID.randomUUID();
		this.code = code;
		this.name = name;
		this.country = country;
	}
}