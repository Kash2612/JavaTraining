package com.example.student.student_SB.controller;

import com.example.student.student_SB.dto.StudentDTO;
import com.example.student.student_SB.entity.StudentEntity;
import com.example.student.student_SB.exception.StudentNotFoundException;
import com.example.student.student_SB.service.StudentService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<StudentDTO> getAllStudents() {
        return studentService.getAllStudents().stream()
                .map(student -> modelMapper.map(student, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) throws StudentNotFoundException {
        StudentEntity student = studentService.getStudentById(id);
        StudentDTO studentDTO = modelMapper.map(student, StudentDTO.class);
        return ResponseEntity.ok(studentDTO);
    }

    @PostMapping
    public ResponseEntity<StudentDTO> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        StudentDTO student = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(modelMapper.map(student, StudentDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> updateStudent(@PathVariable Long id, @RequestBody @Valid StudentDTO studentDTO) throws StudentNotFoundException {
        StudentEntity student = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(modelMapper.map(student, StudentDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) throws StudentNotFoundException {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
