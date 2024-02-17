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
import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "passengers")
public class Passenger {
	@Id
	@GeneratedValue
	private UUID passengerId;

	@Column(nullable = false)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "flight_id", nullable = false)
	private Flight flight;
}