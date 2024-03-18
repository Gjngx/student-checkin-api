package com.example.studentcheckinbackend.models;


public class AClassDTO {
    private String teacherID;

    private String courseID;

    private String className;

    private Long numberOfSessions;

    public AClassDTO() {
    }

    public AClassDTO(String teacherID, String courseID, String className, Long numberOfSessions) {
        this.teacherID = teacherID;
        this.courseID = courseID;
        this.className = className;
        this.numberOfSessions = numberOfSessions;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
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
}
