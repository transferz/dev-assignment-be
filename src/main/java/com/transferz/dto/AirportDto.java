package com.transferz.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AirportDto
{
	@NotBlank(message = "Name is required")
	@Size(max = 255, message = "Name must not exceed 255 characters")
	private String name;

	@NotBlank(message = "Code is required")
	@Size(max = 20, message = "Code must not exceed 20 characters")
	private String code;

	@NotBlank(message = "Country is required")
	@Size(max = 60, message = "Country must not exceed 60 characters")
	private String country;
}
