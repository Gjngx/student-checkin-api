package com.example.studentcheckinbackend.services;

import com.example.studentcheckinbackend.models.AClass;
import com.example.studentcheckinbackend.models.Student;
import com.example.studentcheckinbackend.models.StudentDTO;
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
    public StudentDTO CreateShowStudent (Student student){
        StudentDTO showStudent = new StudentDTO();
        showStudent.setStudentID(student.getStudentID());
        showStudent.setaClassID(student.getaClass().getClassID());
        showStudent.setFirstName(student.getFirstName());
        showStudent.setLastName(student.getLastName());
        showStudent.setImage(student.getImage());
        showStudent.setDateOfBirth(student.getDateOfBirth());
        showStudent.setAddress(student.getAddress());
        showStudent.setEmail(student.getEmail());
        showStudent.setPhoneNumber(student.getPhoneNumber());
        return showStudent;
    }

    public Student CreateStudent (StudentDTO student, AClass aClass){
        Student createStudent = new Student();
        createStudent.setStudentID(student.getStudentID());
        createStudent.setaClass(aClass);
        createStudent.setFirstName(student.getFirstName());
        createStudent.setLastName(student.getLastName());
        createStudent.setImage(student.getImage());
        createStudent.setDateOfBirth(student.getDateOfBirth());
        createStudent.setAddress(student.getAddress());
        createStudent.setEmail(student.getEmail());
        createStudent.setPhoneNumber(student.getPhoneNumber());
        return createStudent;
    }

    public void UpdateStudent (Long id, StudentDTO newStudent, AClass aClass){
        Student updatedStudent = studentRepository.findById(id)
                .map(student -> {
                    student.setaClass(aClass);
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
    }

}
