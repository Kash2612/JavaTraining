package com.example.student.student_SB.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String courseName;
    private String description;

    // Many-to-One relationship (many courses can be linked to one student)
    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentEntity student;
}
