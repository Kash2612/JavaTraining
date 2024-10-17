package com.example.student.student_SB.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    @NotNull(message = "Project name cannot be null")
    private String projectName;
    private String projectDescription;
}
