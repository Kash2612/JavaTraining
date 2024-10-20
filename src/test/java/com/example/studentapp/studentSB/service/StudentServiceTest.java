package com.example.studentapp.studentSB.service;

import com.example.studentapp.studentSB.dto.StudentDTO;
import com.example.studentapp.studentSB.entity.StudentEntity;
import com.example.studentapp.studentSB.exception.StudentNotFoundException;
import com.example.studentapp.studentSB.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class StudentServiceTest {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateStudent() {
        // Arrange
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setName("John Doe");
        studentDTO.setEmail("john.doe@example.com");

        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setName("John Doe");
        studentEntity.setEmail("john.doe@example.com");

        when(modelMapper.map(any(StudentDTO.class), eq(StudentEntity.class))).thenReturn(studentEntity);
        when(studentRepository.save(any(StudentEntity.class))).thenReturn(studentEntity);

        // Act
        StudentEntity createdStudent = studentService.createStudent(studentDTO);

        // Assert
        assertNotNull(createdStudent);
        assertEquals("John Doe", createdStudent.getName());
    }

    @Test
    void testGetStudentById_Success() throws StudentNotFoundException {
        // Arrange
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setId(1L);
        studentEntity.setName("John Doe");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(studentEntity));

        // Act
        Optional<StudentEntity> student = studentService.getStudentById(1L);

        // Assert
        assertTrue(student.isPresent());
        assertEquals("John Doe", student.get().getName());
    }

    @Test
    void testGetStudentById_ThrowsException() {
        // Arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            studentService.getStudentById(1L);
        });
    }
}
