package com.transferz.dao;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "airports")
@Data
public class Airport
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(nullable = false, length = 255)
	private String name;

	@Column(nullable = false, length = 20)
	private String code;

	@Column(nullable = false, length = 60)
	private String country;
	
}
