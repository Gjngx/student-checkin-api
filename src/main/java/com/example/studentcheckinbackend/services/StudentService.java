package com.example.studentcheckinbackend.services;

import com.example.studentcheckinbackend.models.AClass;
import com.example.studentcheckinbackend.models.Student;
import com.example.studentcheckinbackend.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public final ClassService classService;
    @Autowired
    public StudentService(StudentRepository studentRepository, ClassService classService) {
        this.studentRepository = studentRepository;
        this.classService = classService;
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

    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }

    public boolean isEmailExists(String email) {
        Optional<Student> existingStudent = studentRepository.findByEmail(email);
        return existingStudent.isPresent();
    }
    public boolean isPhoneNumberExists(String phoneNumber) {
        Optional<Student> existingStudent = studentRepository.findByPhoneNumber(phoneNumber);
        return existingStudent.isPresent();
    }
}
