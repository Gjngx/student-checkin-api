package com.example.studentcheckinbackend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student {
    @Id
    @Column(name = "studentID")
    private Long studentID;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Attendance> attendance;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enrollments> enrollments;

    @NotBlank(message = "Vui lòng điền tên sinh viên")
    @Size(max = 10, message = "Tên không quá 10 ký tự")
    @Column(name = "firstName")
    private String firstName;
    @NotBlank(message = "Vui lòng điền họ sinh viên")
    @Size(max = 25, message = "Họ không quá 25 ký tự")
    @Column(name = "lastName")
    private String lastName;
    @NotEmpty(message = "Vui lòng chọn file ảnh")
    @Column(name = "image")
    private String image;
    @Past(message = "Ngày sinh không chính xác")
    @Column(name = "DateOfBirth")
    private Date dateOfBirth;
    @Size(max = 150, message = "Địa chỉ không quá 150 ký tự")
    @Column(name = "address")
    private String address;
    @Email(message = "Email Không chính xác")
    @Column(name = "email")
    private String email;
    @Pattern(regexp = "(84|0[3|5|7|8|9])+([0-9]{8})\\b", message = "Số điện thoại không chính xác")
    @Column(name = "phoneNumber")
    private String phoneNumber;

    public Student(){}

    public Student(Long studentID, String firstName, String lastName, String image, Date dateOfBirth, String address, String email, String phoneNumber) {
        this.studentID = studentID;
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
        return "Student{" +
                "studentID=" + studentID +
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
