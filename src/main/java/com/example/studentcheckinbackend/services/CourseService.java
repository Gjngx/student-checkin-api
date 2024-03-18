package com.example.studentcheckinbackend.services;


import com.example.studentcheckinbackend.models.Course;
import com.example.studentcheckinbackend.models.Room;
import com.example.studentcheckinbackend.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    public final CourseRepository courseRepository;
    @Autowired
    public CourseService (CourseRepository courseRepository){
        this.courseRepository = courseRepository;
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
    public boolean isCourseNameExists(String courseName) {
        Optional<Course> existingCourse = courseRepository.findByCourseName(courseName);
        return existingCourse.isPresent();
    }
    public Course getCourseById(String courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }
    public boolean isCourseIdExists(String courseID) {
        return courseRepository.existsByCourseID(courseID);
    }
}
