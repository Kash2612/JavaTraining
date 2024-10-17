package com.example.studentapp.studentSB.repository;


import com.example.studentapp.studentSB.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
