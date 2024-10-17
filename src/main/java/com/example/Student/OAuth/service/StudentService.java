package com.example.Student.OAuth.service;


import com.example.Student.OAuth.dto.StudentDTO;
import com.example.Student.OAuth.entity.StudentEntity;
import com.example.Student.OAuth.exception.StudentNotFoundException;
import com.example.Student.OAuth.repository.StudentRepository;
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
    private ModelMapper modelMapper;

    public List<StudentEntity> getAllStudents() {

        return studentRepository.findAll();
    }

    public Optional<StudentEntity> getStudentById(Long id) throws StudentNotFoundException {
        Optional<StudentEntity> student= studentRepository.findById(id);
        if(student.isPresent()){
            return student;
        }
        else{
            throw new StudentNotFoundException("student nto found with id: "+id);
        }
    }

//    public StudentEntity createStudent(StudentDTO studentDTO) {
//        StudentEntity student = new StudentEntity();
//        student.setName(studentDTO.getName());
//        student.setEmail(studentDTO.getEmail());
//        student.setCourse(studentDTO.getCourse());
//        return studentRepository.save(student);
//    }

        public StudentEntity createStudent(StudentDTO studentDTO) {
        // Map StudentDTO to StudentEntity
        StudentEntity student = modelMapper.map(studentDTO, StudentEntity.class);
        return studentRepository.save(student);
    }

//    public StudentEntity updateStudent(Long id, StudentDTO studentDTO) {
//        Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
//        if (optionalStudent.isPresent()) {
//            StudentEntity student = optionalStudent.get();
//            student.setName(studentDTO.getName());
//            student.setEmail(studentDTO.getEmail());
//            student.setCourse(studentDTO.getCourse());
//            return studentRepository.save(student);
//        }
//        return null;
//    }



    public StudentEntity updateStudent(Long id, StudentDTO studentDTO) {
        Optional<StudentEntity> optionalStudent = studentRepository.findById(id);
        if (optionalStudent.isPresent()) {
            StudentEntity student = optionalStudent.get();
            // Map fields from StudentDTO to StudentEntity
            modelMapper.map(studentDTO, student);
            return studentRepository.save(student);
        }
        return null;
    }


    public boolean deleteStudent(Long id) {
        boolean isPresent=studentRepository.existsById(id);
        if(!isPresent){
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }
}

