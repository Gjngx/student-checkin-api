package com.example.studentcheckinbackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Enrollments")
public class Enrollments {
    @Id
    @Column(name = "enrollmentID")
    private String enrollmentID;

    @ManyToOne
    @JoinColumn(name = "studentID")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "classID")
    private AClass aClass;

}
