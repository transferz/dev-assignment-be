package com.transferz.dao;

import com.transferz.enums.FlightStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FLIGHT")
public class Flight {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;
  @Column(name = "CODE", length = 20, nullable = false)
  private String code;

  @Column(name = "ORIGIN_AIRPORT_CODE", length = 20, nullable = false)
  private String originAirportId;

  @Column(name = "DESTINATION_AIRPORT_CODE", length = 20, nullable = false)
  private String destinationAirportId;

  @Column(name = "DEPATRUE_DATE_TIME")
  private LocalDateTime departureTime;

  @Column(name = "ARRIVAL_DATE_TIME")
  private LocalDateTime arrivalTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "FLIGHT_STATUS")
  private FlightStatus flightStatus;

  @Column(name = "PASSENGER_COUNT")
  private int passengerCount = 0;

}
