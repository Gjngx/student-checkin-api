package com.example.studentcheckinbackend.services;

import com.example.studentcheckinbackend.models.Teacher;
import com.example.studentcheckinbackend.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    @Autowired
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    public List<String> getValidationErrors(BindingResult bindingResult) {
        List<String> errorMessages = new ArrayList<>();

        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();

            for (ObjectError error : errors) {
                errorMessages.add(error.getDefaultMessage());
            }
        }

        return errorMessages;
    }

    public Teacher getTeacherById(String teacherId) {
        return teacherRepository.findById(teacherId).orElse(null);
    }

    public boolean isEmailExists(String email) {
        Optional<Teacher> existingTeacher = teacherRepository.findByEmail(email);
        return existingTeacher.isPresent();
    }
    public boolean isPhoneNumberExists(String phoneNumber) {
        Optional<Teacher> existingStudent = teacherRepository.findByPhoneNumber(phoneNumber);
        return existingStudent.isPresent();
    }
}
