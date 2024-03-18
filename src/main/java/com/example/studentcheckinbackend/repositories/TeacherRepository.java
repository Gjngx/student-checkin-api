package com.example.studentcheckinbackend.repositories;

import com.example.studentcheckinbackend.models.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, String> {
    Optional<Teacher> findByEmail(String email);
    Optional<Teacher> findByPhoneNumber(String phoneNumber);
}
