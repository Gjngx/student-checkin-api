package com.example.studentcheckinbackend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table(name = "Course")
public class Course {
    @Id
    @Column(name = "courseID")
    private String courseID;

    @NotBlank(message = "Vui lòng điền tên khóa học")
    @Column(name = "courseName")
    private String courseName;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AClass> aClass;

    public Course() { }

    public Course(String courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


    @Override
    public String toString() {
        return "Course{" +
                "courseID=" + courseID +
                ", courseName='" + courseName + '\'' +
                '}';
    }
}
