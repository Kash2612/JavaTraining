package com.example.Student.OAuth.repository;


import com.example.Student.OAuth.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
