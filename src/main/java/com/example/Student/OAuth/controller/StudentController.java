package com.example.Student.OAuth.controller;


import com.example.Student.OAuth.dto.StudentDTO;
import com.example.Student.OAuth.entity.StudentEntity;
import com.example.Student.OAuth.exception.StudentNotFoundException;
import com.example.Student.OAuth.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String getAllStudents() {

        return "authenticateddddd";
    }

//    @GetMapping
//    public List<StudentEntity> getAllStudents() {
//
//        return studentService.getAllStudents();
//    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEntity> getStudentById(@PathVariable Long id) throws StudentNotFoundException {
        Optional<StudentEntity> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        else{
            throw new StudentNotFoundException("student not found with id "+id);
        }
    }

    @PostMapping
    public ResponseEntity<StudentEntity> createStudent(@RequestBody @Valid StudentDTO studentDTO) {
        StudentEntity student = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentEntity> updateStudent(@PathVariable Long id, @RequestBody StudentDTO studentDTO) {
        StudentEntity updatedStudent = studentService.updateStudent(id, studentDTO);
        if (updatedStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
