package com.example.studentcheckinbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.sql.Time;
import java.util.List;


@Entity
@Table(name = "Schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scheduleID")
    private int scheduleID;

    @ManyToOne
    @JoinColumn(name = "classID")
    private AClass aClass;

    @ManyToOne
    @JoinColumn(name = "roomID")
    private Room room;

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendance;

    @NotBlank(message = "Vui lòng điền ngày học")
    @Column(name = "date")
    private Date date;
    @NotBlank(message = "Vui lòng điền thời gian bắt đầu")
    @Column(name = "startTime")
    private Time startTime;
    @NotBlank(message = "Vui lòng điền thời gian kết thúc")
    @Column(name = "endTime")
    private Time endTime;

}
