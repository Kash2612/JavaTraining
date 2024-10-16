package com.example.student.student_SB.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    @NotNull(message = "this field can't be null")
    private String name;
    @Email(message = "enter valid email")
    private String email;
    private Set<CourseDTO> courses;
}
