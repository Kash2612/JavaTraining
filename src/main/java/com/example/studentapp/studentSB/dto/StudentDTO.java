package com.example.studentapp.studentSB.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    @NotNull(message = "name can't be null")
    private String name;
    @Email(message = "enter valid email")
    private String email;
    private String course;
}
