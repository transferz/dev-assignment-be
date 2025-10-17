package com.transferz.controller;

import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDto;
import com.transferz.service.IPassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("passenger")
public class PassengerController {

  @Autowired
  private IPassengerService passengerService;

  @PostMapping("/create")
  public Passenger getAllPaginatedFlights(@Valid @RequestBody PassengerDto passengerDto) {
    System.out.println("Creating passenger with data: " + passengerDto);
    return passengerService.create(passengerDto);
  }

}
