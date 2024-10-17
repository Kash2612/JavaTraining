package com.example.studentapp.studentSB.service;

import com.example.studentapp.studentSB.dto.StudentDTO;
import com.example.studentapp.studentSB.entity.StudentEntity;
import com.example.studentapp.studentSB.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<StudentEntity> getAllStudents() {
        return studentRepository.findAll();
    }

    public StudentEntity getStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
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
            modelMapper.map(studentDTO, student); // Updates the student entity in place
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

