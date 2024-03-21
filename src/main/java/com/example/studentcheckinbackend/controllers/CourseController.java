package com.example.studentcheckinbackend.controllers;

import com.example.studentcheckinbackend.models.Course;
import com.example.studentcheckinbackend.models.ResponseObject;
import com.example.studentcheckinbackend.models.Room;
import com.example.studentcheckinbackend.models.Teacher;
import com.example.studentcheckinbackend.repositories.CourseRepository;
import com.example.studentcheckinbackend.services.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api/v1/courses")
public class CourseController {
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    @Autowired
    public CourseController(CourseRepository courseRepository, CourseService courseService) {
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }
    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursesPage = courseRepository.findAll(pageable);

        List<Course> courses = coursesPage.getContent();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findCourseById(@PathVariable String id){
        Optional<Course> foundCourse = courseRepository.findById(id);
        if (foundCourse.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Tìm thấy môn học với mã:"+ id , foundCourse)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Mã môn học " + id + " Không tồn tại","")
            );
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseObject> insertCourse(@Valid @RequestBody Course newCourse, BindingResult bindingResult) {

        List<String> errorMessages = courseService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("error", "Lỗi validation", errorMessages)
            );
        }

        Optional<Course> foundCourse = courseRepository.findById(newCourse.getCourseID());

        if (foundCourse.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Mã môn học đã tồn tại","")
            );
        }

        if (courseService.isCourseNameExists(newCourse.getCourseName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Tên môn học đã tồn tại", "")
            );
        }

        Course savedCourse = courseRepository.save(newCourse);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("success", "Đã thêm môn học thành công","")
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateCourse(@Valid @RequestBody Course newCourse, @PathVariable String id, BindingResult bindingResult) {

        List<String> errorMessages = courseService.getValidationErrors(bindingResult);

        if (!errorMessages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Lỗi validation", errorMessages)
            );
        }

        Course checkCourse = courseService.getCourseById(id);

        if (checkCourse == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy môn học có mã: " + id, "")
            );
        }

        if (!checkCourse.getCourseID().equals(newCourse.getCourseID()) && courseService.isCourseIdExists(newCourse.getCourseID())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Mã môn học đã tồn tại", "")
            );
        }

        if (!checkCourse.getCourseName().equals(newCourse.getCourseName())) {
            if (courseService.isCourseNameExists(newCourse.getCourseName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Tên môn học đã tồn tại", "")
                );
            }
        }

        Course updatedCourse = courseRepository.findById(id)
                .map(course -> {
                    course.setCourseName(newCourse.getCourseName());
                    return courseRepository.save(course);
                })
                .orElse(null);

        if (updatedCourse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Cập nhật môn học "+ id +" thành công", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy môn học có mã: " + id, "")
            );
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteCourse (@PathVariable String id){
        boolean exists = courseRepository.existsById(id);
        if(exists){
            courseRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Xoá môn học có mã: "+ id +" thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Môn học không tồn tại!", "")
        );
    }
}
