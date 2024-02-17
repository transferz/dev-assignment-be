package com.transferz.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "airports")
public class Airport {
	@Id
	@GeneratedValue
	private UUID airportId;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String code;

	@Column(nullable = false)
	private String country;
}