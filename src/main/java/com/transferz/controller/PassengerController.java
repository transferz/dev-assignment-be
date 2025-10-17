package com.transferz.controller;

import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDto;
import com.transferz.service.PassengerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController
{
	@Autowired
	private PassengerService passengerService;

	@PostMapping
	public ResponseEntity<Passenger> addPassenger(@Valid @RequestBody PassengerDto passengerDto)
	{
		try
		{
			Passenger passenger = passengerService.addPassenger(passengerDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(passenger);
		}
		catch (IllegalArgumentException e)
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
