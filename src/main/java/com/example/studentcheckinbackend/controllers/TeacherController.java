package com.example.studentcheckinbackend.controllers;

import com.example.studentcheckinbackend.models.ResponseObject;
import com.example.studentcheckinbackend.models.Teacher;
import com.example.studentcheckinbackend.repositories.TeacherRepository;
import com.example.studentcheckinbackend.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping(path = "/api/v1/teachers")
public class TeacherController {
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;
    @Autowired
    public TeacherController(TeacherRepository teacherRepository, TeacherService teacherService) {
        this.teacherRepository = teacherRepository;
        this.teacherService = teacherService;
    }
    @GetMapping("")
    public ResponseEntity<List<Teacher>> getAllTeachers() {
        List<Teacher> teachers = teacherRepository.findAll();
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findTeacherById(@PathVariable String id) {
        Optional<Teacher> foundTeacher = teacherRepository.findById(id);
        if (foundTeacher.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Tìm thấy giảng viên với mã là " + id, foundTeacher)
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "không tìm thấy giảng viên với mã là " + id, "")
            );
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> insertTeacher(@Valid @RequestBody Teacher newTeacher, BindingResult bindingResult) {

        List<String> errorMessages = teacherService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        Optional<Teacher> foundTeacher = teacherRepository.findById(newTeacher.getTeacherID());

        if (foundTeacher.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Mã giảng viên đã tồn tại","")
            );
        }

        if (teacherService.isEmailExists(newTeacher.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Email đã tồn tại", "")
            );
        }

        if (teacherService.isPhoneNumberExists(newTeacher.getPhoneNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Số điện thoại đã tồn tại", "")
            );
        }

        Teacher savedTeacher = teacherRepository.save(newTeacher);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("success", "Đã thêm giảng viên thành công","")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateTeacher(@Valid @RequestBody Teacher newTeacher, @PathVariable String id, BindingResult bindingResult) {

        List<String> errorMessages = teacherService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        Teacher checkTeacher = teacherService.getTeacherById(id);

        if (checkTeacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy giảng viên có mã: " + id, "")
            );
        }

        if (!checkTeacher.getEmail().equals(newTeacher.getEmail())) {
            if (teacherService.isEmailExists(newTeacher.getEmail())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Email đã tồn tại", "")
                );
            }
        }

        if (!checkTeacher.getPhoneNumber().equals(newTeacher.getPhoneNumber())) {
            if (teacherService.isPhoneNumberExists(newTeacher.getPhoneNumber())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Số điện thoại đã tồn tại", "")
                );
            }
        }

        Teacher updatedTeacher = teacherRepository.findById(id)
                .map(teacher -> {
                    teacher.setFirstName(newTeacher.getFirstName());
                    teacher.setLastName(newTeacher.getLastName());
                    teacher.setEmail(newTeacher.getEmail());
                    teacher.setPhoneNumber(newTeacher.getPhoneNumber());
                    return teacherRepository.save(teacher);
                })
                .orElse(null);

        if (updatedTeacher != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Cập nhật giảng viên "+ id +" thành công", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy giảng viên có mã: " + id, "")
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteTeacher(@PathVariable String id){
        boolean exists = teacherRepository.existsById(id);
        if(exists){
            teacherRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Xoá giảng viên có mã"+ id +" thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "giảng viên không tồn tại!", "")
        );
    }

}
