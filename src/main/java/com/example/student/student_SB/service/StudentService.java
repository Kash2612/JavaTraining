package com.example.student.student_SB.service;

//import com.example.student.student_SB.dto.StudentDTO;
//import com.example.student.student_SB.entity.AddressEntity;
//import com.example.student.student_SB.entity.CourseEntity;
//import com.example.student.student_SB.entity.StudentEntity;
//import com.example.student.student_SB.repository.CourseRepository;
//import com.example.student.student_SB.repository.StudentRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.modelmapper.ModelMapper;
//
//
//import javax.swing.text.html.Option;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class StudentService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private CourseRepository courseRepository;
//
//    @Autowired
//    private ModelMapper modelMapper;
//
//    public List<StudentEntity> getAllStudents() {
//        return studentRepository.findAll();
//    }
//
//    public StudentEntity getStudentById(Long id) {
//        return studentRepository.findById(id).orElse(null);
//    }
//
////    public StudentEntity createStudent(StudentDTO studentDTO) {
////        StudentEntity student = new StudentEntity();
////        student.setName(studentDTO.getName());
////        student.setEmail(studentDTO.getEmail());
////        student.setCourse(studentDTO.getCourse());
////        return studentRepository.save(student);
////    }
//
////    public StudentEntity createStudent(StudentDTO studentDTO) {
////        // Map StudentDTO to StudentEntity
////        StudentEntity student = modelMapper.map(studentDTO, StudentEntity.class);
////        return studentRepository.save(student);
////    }
//public StudentEntity createStudent(StudentDTO studentDTO) {
//    StudentEntity student = modelMapper.map(studentDTO, StudentEntity.class);
//
//    // Mapping courses, projects, and address
//    Set<CourseEntity> courses = studentDTO.getCourseIds().stream()
//            .map(id -> courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found")))
//            .collect(Collectors.toSet());
//    student.setCourses(courses);
//
//    if (studentDTO.getAddress() != null) {
//        AddressEntity address = modelMapper.map(studentDTO.getAddress(), AddressEntity.class);
//        student.setAddress(address);
//    }
//
//    return studentRepository.save(student);
//}
//
////    public StudentEntity updateStudent(Long id, StudentDTO studentDTO) {
////        Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
////        if (optionalStudent.isPresent()) {
////            StudentEntity student = optionalStudent.get();
////            student.setName(studentDTO.getName());
////            student.setEmail(studentDTO.getEmail());
////            student.setCourse(studentDTO.getCourse());
////            return studentRepository.save(student);
////        }
////        return null;
////    }
//
//    public StudentEntity updateStudent(Long id, StudentDTO studentDTO) {
//        StudentEntity student = studentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Student not found with ID: " + id));
//
//        modelMapper.map(studentDTO, student);
//
//        // Update address
//        if (studentDTO.getAddress() != null) {
//            AddressEntity address = modelMapper.map(studentDTO.getAddress(), AddressEntity.class);
//            student.setAddress(address);
//        }
//
//        return studentRepository.save(student);
//    }
//    public boolean deleteStudent(Long id) {
//        boolean isPresent=studentRepository.existsById(id);
//        if(!isPresent){
//            return false;
//        }
//        studentRepository.deleteById(id);
//        return true;
//    }
//}


import com.example.student.student_SB.dto.StudentDTO;
import com.example.student.student_SB.entity.AddressEntity;
import com.example.student.student_SB.entity.StudentEntity;
import com.example.student.student_SB.exception.StudentNotFoundException;
import com.example.student.student_SB.repository.AddressRepository;
import com.example.student.student_SB.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public StudentEntity getStudentById(Long id) throws StudentNotFoundException {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    @Transactional
    public StudentDTO createStudent(StudentDTO studentDTO) {
        AddressEntity addressEntity = modelMapper.map(studentDTO.getAddress(), AddressEntity.class);

        // Save the address entity first
        addressRepository.save(addressEntity);

        StudentEntity studentEntity = modelMapper.map(studentDTO, StudentEntity.class);
        studentEntity.setAddress(addressEntity);

        // Now save the student entity
        studentRepository.save(studentEntity);

        return modelMapper.map(studentEntity, StudentDTO.class);
    }


    public StudentEntity updateStudent(Long id, StudentDTO studentDTO) throws StudentNotFoundException {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
        modelMapper.map(studentDTO, student);
        return studentRepository.save(student);
    }

    public boolean deleteStudent(Long id) throws StudentNotFoundException {
        boolean exists = studentRepository.existsById(id);
        if (!exists) {
            throw new StudentNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
        return true;
    }
}
