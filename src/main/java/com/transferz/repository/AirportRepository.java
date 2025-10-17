package com.transferz.repository;

import com.transferz.dao.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Long>
{
	Page<Airport> findByNameContainingOrCodeContaining(String name, String code, Pageable pageable);

	Optional<Airport> findByCode(String code);

	Optional<Airport> findByName(String name);
}
