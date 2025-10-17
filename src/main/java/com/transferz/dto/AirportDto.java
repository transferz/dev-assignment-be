package com.transferz.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AirportDto {
    private String name;
    private String code;
    private String country;
}
