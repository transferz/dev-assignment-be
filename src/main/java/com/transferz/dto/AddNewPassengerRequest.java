package com.transferz.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddNewPassengerRequest {
    @Size(min = 3, max = 200, message = "Length of the name should be 3-200 characters")
    @NotBlank(message = "Name must not be blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Invalid input for Name: Only letters are allowed.")
    private String name;

}
