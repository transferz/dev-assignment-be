package com.transferz.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class PassengerDto
{
	@NotBlank(message = "Name is required")
	@Size(max = 1024, message = "Name must not exceed 1024 characters")
	private String name;

	@NotBlank(message = "Flight code is required")
	@Size(max = 20, message = "Flight code must not exceed 20 characters")
	private String flightCode;
}
