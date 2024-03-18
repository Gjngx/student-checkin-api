package com.example.studentcheckinbackend.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @Column(name = "teacherID")
    private String teacherID;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AClass> aClass;

    @NotBlank(message = "Vui lòng điền tên giảng viên")
    @Size(max = 10, message = "Tên không quá 10 ký tự")
    @Column(name = "firstName")
    private String firstName;
    @NotBlank(message = "Vui lòng điền họ giảng viên")
    @Size(max = 25, message = "Họ không quá 25 ký tự")
    @Column(name = "lastName")
    private String lastName;
    @Email(message = "Email Không chính xác")
    @Column(name = "email")
    private String email;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không chính xác")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Teacher(){

    }

    public Teacher(String teacherID, String firstName, String lastName, String email, String phoneNumber) {
        this.teacherID = teacherID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(String teacherID) {
        this.teacherID = teacherID;
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
        return "Teacher{" +
                "teacherID=" + teacherID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
