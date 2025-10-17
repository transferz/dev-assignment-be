package com.transferz.repository;

import com.transferz.dao.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long>
{
	Optional<Flight> findByCode(String code);

	@Query("SELECT f FROM Flight f WHERE f.passengerCount < :maxPassengers ORDER BY f.departureDateTime ASC")
	List<Flight> findAvailableFlights(Integer maxPassengers);
}
