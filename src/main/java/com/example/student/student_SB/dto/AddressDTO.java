package com.example.student.student_SB.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {
    @NotNull(message = "Street cannot be null")
    private String street;

    @NotNull(message = "City cannot be null")
    private String city;

    @NotNull(message = "State cannot be null")
    private String state;

    @NotNull(message = "Zip code cannot be null")
    private String zipCode;
}
