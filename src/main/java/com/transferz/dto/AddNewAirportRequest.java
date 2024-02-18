package com.transferz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddNewAirportRequest {
    @Size(min = 3, max = 200, message = "Length of the name should be 3-200 characters")
    @NotBlank(message = "Name must not be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid input for Name: Only letters are allowed.")
    private String name;

    @NotBlank(message = "Code must not be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid input for Code: Only letters are allowed.")
    @Size(min = 2, max = 5, message = "Length of the code should be 2-5 characters")
    private String code;

    @NotBlank(message = "Country must not be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid input for Country: Only letters are allowed.")
    @Size(min = 3, max = 200, message = "Length of the country should be 3-200 characters")
    private String country;

}
