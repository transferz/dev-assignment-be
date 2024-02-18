package com.transferz.repository;

import com.transferz.dao.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    @Query(value = "SELECT * FROM Flight WHERE is_Full_Capacity = FALSE ORDER BY departure_Time LIMIT 1", nativeQuery = true)
    Optional<Flight> findFirstAvailableFlight();


}
