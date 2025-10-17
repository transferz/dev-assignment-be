package com.transferz.dao;

import com.transferz.dto.AirportDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "AIRPORT")
public class Airport {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(
      name = "UUID",
      strategy = "org.hibernate.id.UUIDGenerator"
  )
  private UUID id;

  @Column(name = "NAME", nullable = false)
  private String name;

  @Column(name = "CODE", length = 20, nullable = false)
  private String code;

  @Column(name = "COUNTRY", length = 60, nullable = false)
  private String country;

  public Airport(AirportDto dto) {
    this.name = dto.getName();
    this.code = dto.getCode();
    this.country = dto.getCountry();
  }
}
