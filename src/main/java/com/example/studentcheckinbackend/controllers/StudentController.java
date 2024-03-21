package com.example.studentcheckinbackend.controllers;


import com.example.studentcheckinbackend.models.ResponseObject;
import com.example.studentcheckinbackend.models.Student;
import com.example.studentcheckinbackend.repositories.StudentRepository;
import com.example.studentcheckinbackend.services.ClassService;
import com.example.studentcheckinbackend.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentService studentService;
    public final ClassService classService;
    @Autowired
    public StudentController(StudentRepository studentRepository, StudentService studentService, ClassService classService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
        this.classService = classService;
    }
    @GetMapping("")
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findStudentById(@PathVariable Long id){
        Optional<Student> foundStudent = studentRepository.findById(id);
        if(foundStudent.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Tìm thấy sinh viên với mã sinh viên là "+ id , foundStudent)
            );
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "không tìm thấy sinh viên với mã sinh viên là " + id, "")
            );
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> insertStudent(@Valid @RequestBody Student newStudent, BindingResult bindingResult) {

        List<String> errorMessages = studentService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        Optional<Student> foundStudent = studentRepository.findById(newStudent.getStudentID());

        if (foundStudent.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Mã sinh viên đã tồn tại","")
            );
        }


        if (studentService.isEmailExists(newStudent.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Email đã tồn tại", "")
            );
        }

        if (studentService.isPhoneNumberExists(newStudent.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Số điện thoại đã tồn tại", "")
            );
        }

        Student savedStudent = studentRepository.save(newStudent);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("success", "Đã thêm sinh viên thành công","")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateStudent(@Valid @RequestBody Student newStudent, @PathVariable Long id, BindingResult bindingResult) {

        List<String> errorMessages = studentService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        Student checkStudent = studentService.getStudentById(id);

        if (checkStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy sinh viên có mã sinh viên: " + id, "")
            );
        }

        if (!checkStudent.getEmail().equals(newStudent.getEmail())) {
            if (studentService.isEmailExists(newStudent.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Email đã tồn tại", "")
                );
            }
        }

        if (!checkStudent.getPhoneNumber().equals(newStudent.getPhoneNumber())) {
            if (studentService.isPhoneNumberExists(newStudent.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Số điện thoại đã tồn tại", "")
                );
            }
        }

        Student updatedStudent = studentRepository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setLastName(newStudent.getLastName());
                    student.setAddress(newStudent.getAddress());
                    student.setImage(newStudent.getImage());
                    student.setEmail(newStudent.getEmail());
                    student.setDateOfBirth(newStudent.getDateOfBirth());
                    student.setPhoneNumber(newStudent.getPhoneNumber());
                    return studentRepository.save(student);
                })
                .orElse(null);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("success", "Cập nhật sinh viên " + id + " thành công", "")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteStudent(@PathVariable Long id){
        boolean exists = studentRepository.existsById(id);
        if(exists){
            studentRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Xoá sinh viên có mã"+ id +" thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Sinh viên không tồn tại!", "")
        );
    }

}
