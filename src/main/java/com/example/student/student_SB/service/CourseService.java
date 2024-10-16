package com.example.student.student_SB.service;

import com.example.student.student_SB.dto.CourseDTO;
import com.example.student.student_SB.entity.CourseEntity;
import com.example.student.student_SB.entity.StudentEntity;
import com.example.student.student_SB.repository.CourseRepository;
import com.example.student.student_SB.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    // Create a course for a student
    public CourseEntity createCourse(Long studentId, CourseDTO courseDTO) {
        CourseEntity course = modelMapper.map(courseDTO, CourseEntity.class);
        studentRepository.findById(studentId).ifPresent(course::setStudent);
        return courseRepository.save(course);
    }

    // Update a course
    public CourseEntity updateCourse(Long id, CourseDTO courseDTO) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            CourseEntity course = optionalCourse.get();
            modelMapper.map(courseDTO, course);  // Updates course fields
            return courseRepository.save(course);
        }
        return null;
    }

    // Get all courses for a student
    public Set<CourseEntity> getCoursesByStudent(Long studentId) {
        return studentRepository.findById(studentId).map(StudentEntity::getCourses).orElse(null);
    }

    // Delete a course
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
