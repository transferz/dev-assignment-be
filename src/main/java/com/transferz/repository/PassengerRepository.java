package com.transferz.repository;

import com.transferz.dao.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {

    Optional<List<Passenger>> findAllByFlightCode(String airportCode);


}
