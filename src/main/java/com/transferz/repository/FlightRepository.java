package com.transferz.repository;

import com.transferz.dao.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FlightRepository extends JpaRepository<Flight, String> {

    /**
     * This method returns flight which came first with defined flightStatus.
     *
     * @param flightStatus
     * @return Optional<Flight>
     */
    @Query(value = "SELECT * FROM FLIGHT FL WHERE FL.FLIGHT_STATUS = :flightStatus ORDER BY FL.ARRIVAL_DATE_TIME ASC LIMIT 1", nativeQuery = true)
    Optional<Flight> getFirstFlightByStatus(@Param("flightStatus") String flightStatus);

}
