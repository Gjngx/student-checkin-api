package com.example.studentcheckinbackend.models;

public class AClassShowDTO {

    private Long AClassID;

    private String teacherID;

    private String teacherName;

    private String courseID;

    private String courseName;

    private String className;

    private Long numberOfSessions;

    public AClassShowDTO() {
    }

    public AClassShowDTO(Long AClassID, String teacherID, String teacherName, String courseID, String courseName, String className, Long numberOfSessions) {
        this.AClassID = AClassID;
        this.teacherID = teacherID;
        this.teacherName = teacherName;
        this.courseID = courseID;
        this.courseName = courseName;
        this.className = className;
        this.numberOfSessions = numberOfSessions;
    }

    public Long getAClassID() {
        return AClassID;
    }

    public void setAClassID(Long AClassID) {
        this.AClassID = AClassID;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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
        return "AClassShowDTO{" +
                "AClassID=" + AClassID +
                ", teacherID='" + teacherID + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", courseID='" + courseID + '\'' +
                ", courseName='" + courseName + '\'' +
                ", className='" + className + '\'' +
                ", numberOfSessions=" + numberOfSessions +
                '}';
    }
}