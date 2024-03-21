package com.example.studentcheckinbackend.services;

import com.example.studentcheckinbackend.models.AClass;
import com.example.studentcheckinbackend.models.AClassShowDTO;
import com.example.studentcheckinbackend.models.Course;
import com.example.studentcheckinbackend.repositories.ClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClassService {
    public final ClassRepository classRepository;
    @Autowired
    public ClassService (ClassRepository classRepository){
        this.classRepository = classRepository;
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
    public boolean isClassNameExists(String className) {
        Optional<AClass> existingClass = classRepository.findByClassName(className);
        return existingClass.isPresent();
    }
    public AClass getClassById(Long classId) {
        return classRepository.findById(classId).orElse(null);
    }
    public boolean isClassIdExists(Long classId) {
        return classRepository.existsByClassID(classId);
    }
    public AClassShowDTO createAClassShowDTO(AClass aClass) {
        return new AClassShowDTO(
                aClass.getClassID(),
                aClass.getTeacher().getTeacherID(),
                aClass.getTeacher().getLastName() + " " + aClass.getTeacher().getFirstName(),
                aClass.getCourse().getCourseID(),
                aClass.getCourse().getCourseName(),
                aClass.getClassName(),
                aClass.getNumberOfSessions()
        );
    }
}
