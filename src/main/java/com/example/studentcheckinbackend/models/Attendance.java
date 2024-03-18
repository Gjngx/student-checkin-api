package com.example.studentcheckinbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "attendanceID")
    private int attendanceID;

    @ManyToOne
    @JoinColumn(name = "scheduleID")
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "StudentID")
    private Student student;

    @NotBlank(message = "Vui lòng điền ngày điểm danh")
    @Column(name = "date")
    private Date date;

    @NotBlank(message = "Vui lòng điền thời gian điểm danh")
    @Column(name = "time")
    private Time time;

    @NotBlank(message = "Vui lòng điền trạng thái điểm danh")
    @Size(max = 15, message = "Trạng thái không quá 15 ký tự")
    @Column(name = "status")
    private String status;

}
