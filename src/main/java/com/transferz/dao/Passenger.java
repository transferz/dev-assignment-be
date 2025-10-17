package com.transferz.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "passengers")
@Data
public class Passenger
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 1024)
	private String name;

	@Column(nullable = false, length = 20)
	private String flightCode;

}
