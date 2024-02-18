package com.transferz.repository;

import com.transferz.dao.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, String> {

    @Query(value = "SELECT code FROM airport ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Optional<String> findAvailableAirportCode();

    @Query("SELECT a FROM Airport a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(a.code) LIKE LOWER(CONCAT('%', :code, '%'))")
    Page<Airport> findByNameAndCodeContainingIgnoreCase(String name, String code, Pageable pageable);

    @Query("SELECT a FROM Airport a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Airport> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT a FROM Airport a WHERE LOWER(a.code) LIKE LOWER(CONCAT('%', :code, '%'))")
    Page<Airport> findByCodeContainingIgnoreCase(String code, Pageable pageable);

}
