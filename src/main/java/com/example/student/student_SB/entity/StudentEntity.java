package com.example.student.student_SB.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String course; // This field will be removed since we're introducing relationships.

    // One-to-One relationship
    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Address address;  // Hypothetical Address entity

    // One-to-Many relationship (a student can enroll in multiple courses)
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<CourseEntity> courses;

    // Many-to-Many relationship (students can have multiple projects and vice versa)
    @ManyToMany
    @JoinTable(
            name = "student_project",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<ProjectEntity> projects;
}
