package com.example.student.student_SB.service;

import com.example.student.student_SB.dto.CourseDTO;
import com.example.student.student_SB.entity.CourseEntity;
import com.example.student.student_SB.exception.CourseNotFoundException;
import com.example.student.student_SB.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<CourseEntity> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseEntity getCourseById(Long id) throws CourseNotFoundException {
        return courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
    }

    public CourseEntity createCourse(CourseDTO courseDTO) {
        CourseEntity course = modelMapper.map(courseDTO, CourseEntity.class);
        return courseRepository.save(course);
    }

    public CourseEntity updateCourse(Long id, CourseDTO courseDTO) throws CourseNotFoundException {
        CourseEntity course = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        modelMapper.map(courseDTO, course);
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) throws CourseNotFoundException {
        if (!courseRepository.existsById(id)) {
            throw new CourseNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }
}
