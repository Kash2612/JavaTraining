package com.example.student.student_SB.controller;


import com.example.student.student_SB.dto.CourseDTO;
import com.example.student.student_SB.entity.CourseEntity;
import com.example.student.student_SB.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/students/{studentId}/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Create a course for a student
    @PostMapping
    public ResponseEntity<CourseEntity> createCourse(@PathVariable Long studentId, @RequestBody CourseDTO courseDTO) {
        CourseEntity course = courseService.createCourse(studentId, courseDTO);
        return ResponseEntity.ok(course);
    }

    // Update a course
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseEntity> updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO) {
        CourseEntity updatedCourse = courseService.updateCourse(courseId, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    // Get all courses for a student
    @GetMapping
    public ResponseEntity<Set<CourseEntity>> getCoursesByStudent(@PathVariable Long studentId) {
        Set<CourseEntity> courses = courseService.getCoursesByStudent(studentId);
        return ResponseEntity.ok(courses);
    }

    // Delete a course
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        courseService.deleteCourse(courseId);
        return ResponseEntity.noContent().build();
    }
}
