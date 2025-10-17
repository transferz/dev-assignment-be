package com.transferz.service;

import com.transferz.dao.Passenger;
import com.transferz.dto.PassengerDto;

public interface IPassengerService {

  Passenger create(PassengerDto passenger);
}
