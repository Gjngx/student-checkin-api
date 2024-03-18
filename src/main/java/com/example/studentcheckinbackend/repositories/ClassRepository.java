package com.example.studentcheckinbackend.repositories;

import com.example.studentcheckinbackend.models.AClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<AClass, Long> {
    Optional<AClass> findByClassName (String className);
    boolean existsByClassID(Long courseID);
}
