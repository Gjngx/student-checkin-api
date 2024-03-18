package com.example.studentcheckinbackend.repositories;

import com.example.studentcheckinbackend.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {
    Optional<Course> findByCourseName(String courseName);
    boolean existsByCourseID(String courseID);
}
