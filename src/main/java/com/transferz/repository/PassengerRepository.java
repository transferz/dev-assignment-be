package com.transferz.repository;

import com.transferz.dao.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, String> {

    @Query(value = "SELECT P.NAME FROM PASSENGER P WHERE P.FLIGHT_CODE = :flightCode", nativeQuery = true)
    List<String> getAllPassengersNamesPerFlight(@Param("flightCode") String flightCode);

}
