package com.example.student.student_SB.controller;


import com.example.student.student_SB.dto.CourseDTO;
import com.example.student.student_SB.entity.CourseEntity;
import com.example.student.student_SB.exception.CourseNotFoundException;
import com.example.student.student_SB.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.getAllCourses().stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) throws CourseNotFoundException {
        CourseEntity course = courseService.getCourseById(id);
        return ResponseEntity.ok(modelMapper.map(course, CourseDTO.class));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@RequestBody @Valid CourseDTO courseDTO) {
        CourseEntity course = courseService.createCourse(courseDTO);
        return ResponseEntity.ok(modelMapper.map(course, CourseDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(@PathVariable Long id, @RequestBody @Valid CourseDTO courseDTO) throws CourseNotFoundException {
        CourseEntity updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(modelMapper.map(updatedCourse, CourseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) throws CourseNotFoundException {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
}
