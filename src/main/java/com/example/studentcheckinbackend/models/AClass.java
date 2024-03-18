package com.example.studentcheckinbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name = "Class")
public class AClass {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "classID")
    private Long classID;

    @ManyToOne
    @JoinColumn(name = "teacherID")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "courseID")
    private Course course;

    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Schedule> schedule;

    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Student> student;

    @NotBlank(message = "Vui lòng điền tên lớp học")
    @Column(name = "className")
    private String className;

    @NotNull(message = "Số buổi học không được để trống")
    @Min(value = 0, message = "Số buổi học phải lớn hơn hoặc bằng 0")
    @Column(name = "numberOfSessions")
    private Long numberOfSessions;

    public AClass() {
    }

    public AClass(Long classID, Teacher teacher, Course course, List<Schedule> schedule, List<Student> student, String className, Long numberOfSessions) {
        this.classID = classID;
        this.teacher = teacher;
        this.course = course;
        this.schedule = schedule;
        this.student = student;
        this.className = className;
        this.numberOfSessions = numberOfSessions;
    }

    public Long getClassID() {
        return classID;
    }

    public void setClassID(Long classID) {
        this.classID = classID;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public List<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<Schedule> schedule) {
        this.schedule = schedule;
    }

    public List<Student> getStudent() {
        return student;
    }

    public void setStudent(List<Student> student) {
        this.student = student;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Long getNumberOfSessions() {
        return numberOfSessions;
    }

    public void setNumberOfSessions(Long numberOfSessions) {
        this.numberOfSessions = numberOfSessions;
    }

    @Override
    public String toString() {
        return "AClass{" +
                "classID=" + classID +
                ", teacher=" + teacher +
                ", course=" + course +
                ", schedule=" + schedule +
                ", student=" + student +
                ", className='" + className + '\'' +
                ", numberOfSessions=" + numberOfSessions +
                '}';
    }
}
