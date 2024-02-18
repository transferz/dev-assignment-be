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
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "airport_id")
	private UUID airportId;

	@Column(unique = true, nullable = false)
	private String code;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String country;
}