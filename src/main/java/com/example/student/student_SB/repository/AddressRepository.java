package com.example.student.student_SB.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.student.student_SB.entity.AddressEntity;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}
