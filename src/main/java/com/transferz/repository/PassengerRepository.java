package com.transferz.repository;

import com.transferz.dao.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long>
{
	Optional<Passenger> findByNameAndFlightCode(String name, String flightCode);

	@Query("SELECT COUNT(p) FROM Passenger p WHERE p.flightCode = :flightCode")
	Long countByFlightCode(String flightCode);

	List<Passenger> findByFlightCode(String flightCode);
}
