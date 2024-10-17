package com.example.student.student_SB.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;


//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class StudentDTO {
//    @NotNull(message = "this field can't be null")
//    private String name;
//    @Email(message = "enter valid email")
//    private String email;
////    private Set<CourseDTO> courses;
//    private AddressDTO address;  // Assuming AddressDTO exists
//    private Set<Long> courseIds=new HashSet<>();;  // For course IDs
//    private Set<Long> projectIds=new HashSet<>();;
//
//}

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private Long id;
    private String name;
    private String email;
    private String course;
    private AddressDTO address;
    private Set<CourseDTO> courses;
    private Set<ProjectDTO> projects;

}