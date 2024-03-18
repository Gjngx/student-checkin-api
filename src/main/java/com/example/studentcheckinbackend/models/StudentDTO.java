package com.example.studentcheckinbackend.models;

import java.sql.Date;

public class StudentDTO {
    private Long studentID;
    private Long aClassID;
    private String firstName;
    private String lastName;
    private String image;
    private Date dateOfBirth;
    private String address;
    private String email;
    private String phoneNumber;

    public StudentDTO() {
    }

    public StudentDTO(Long studentID, Long aClassID, String firstName, String lastName, String image, Date dateOfBirth, String address, String email, String phoneNumber) {
        this.studentID = studentID;
        this.aClassID = aClassID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.image = image;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Long getStudentID() {
        return studentID;
    }

    public void setStudentID(Long studentID) {
        this.studentID = studentID;
    }

    public Long getaClassID() {
        return aClassID;
    }

    public void setaClassID(Long aClassID) {
        this.aClassID = aClassID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "studentID=" + studentID +
                ", aClassID=" + aClassID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", image='" + image + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
