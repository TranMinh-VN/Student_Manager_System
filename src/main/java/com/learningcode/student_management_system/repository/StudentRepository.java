package com.learningcode.student_management_system.repository;

import com.learningcode.student_management_system.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    boolean existsByEmail(String studentEmail);
    boolean existsByEmailAndIdNot(String email, String studentId);
    Optional<Student> findByEmail(String email);
}
