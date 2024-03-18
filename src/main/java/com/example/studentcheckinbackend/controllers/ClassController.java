package com.example.studentcheckinbackend.controllers;

import com.example.studentcheckinbackend.models.*;
import com.example.studentcheckinbackend.repositories.ClassRepository;
import com.example.studentcheckinbackend.services.ClassService;
import com.example.studentcheckinbackend.services.CourseService;
import com.example.studentcheckinbackend.services.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/classes")
public class ClassController {
    public final ClassRepository classRepository;
    public final ClassService classService;
    public final TeacherService teacherService;
    public final CourseService courseService;
    @Autowired
    public ClassController (ClassRepository classRepository, ClassService classService, TeacherService teacherService, CourseService courseService) {
        this.classRepository = classRepository;
        this.classService = classService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }
    @GetMapping("")
    public ResponseEntity<List<AClassShowDTO>> getAllClass() {
        List<AClass> classes = classRepository.findAll();
        List<AClassShowDTO> classShows = new ArrayList<>();

        for (AClass aClass : classes) {
            AClassShowDTO aClassShow = new AClassShowDTO();
            aClassShow.setAClassID(aClass.getClassID());
            aClassShow.setTeacherID(aClass.getTeacher().getTeacherID());
            aClassShow.setCourseID(aClass.getCourse().getCourseID());
            aClassShow.setClassName(aClass.getClassName());
            aClassShow.setNumberOfSessions(aClass.getNumberOfSessions());
            classShows.add(aClassShow);
        }
        return new ResponseEntity<>(classShows, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseObject> findClassById(@PathVariable Long id){
        Optional<AClass> foundClass = classRepository.findById(id);
        if (foundClass.isPresent()){
            AClass aClass = foundClass.get();
            AClassShowDTO aClassShow = new AClassShowDTO (
                    aClass.getClassID(),
                    aClass.getTeacher().getTeacherID(),
                    aClass.getCourse().getCourseID(),
                    aClass.getClassName(),
                    aClass.getNumberOfSessions()
            );
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Tìm thấy lớp học với mã:"+ id , aClassShow)
            );
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Mã lớp học " + id + " Không tồn tại","")
            );
        }
    }
    @PostMapping("")
    public ResponseEntity<ResponseObject> insertClass(@Valid @RequestBody AClassDTO newAClass) {

        if (newAClass.getClassName() == null || newAClass.getClassName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Vui lòng điền tên lớp học", "")
            );
        }

        if (newAClass.getNumberOfSessions() < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject("failed", "Số buổi học phải lớn hơn hoặc bằng 0", "")
            );
        }

        Teacher teacher = teacherService.getTeacherById(newAClass.getTeacherID());
        Course course = courseService.getCourseById(newAClass.getCourseID());

        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Giáo viên không tồn tại", "")
            );
        }

        if (course == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Môn học không tồn tại", "")
            );
        }

        if (classService.isClassNameExists(newAClass.getClassName())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Tên lớp học đã tồn tại", "")
            );
        }

        AClass aClass = new AClass();
        aClass.setTeacher(teacher);
        aClass.setCourse(course);
        aClass.setClassName(newAClass.getClassName());
        aClass.setNumberOfSessions(newAClass.getNumberOfSessions());

        AClass saveAClass = classRepository.save(aClass);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseObject("success", "Đã thêm lớp học thành công","")
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateClass(@Valid @RequestBody AClassShowDTO newAClass, @PathVariable Long id) {

        Teacher teacher = teacherService.getTeacherById(newAClass.getTeacherID());
        Course course = courseService.getCourseById(newAClass.getCourseID());

        if (teacher == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Giáo viên không tồn tại", "")
            );
        }

        if (course == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Môn học không tồn tại", "")
            );
        }

        AClass checkAClass = classService.getClassById(id);

        if (checkAClass == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "Không tìm thấy lớp học có mã: " + id, "")
            );
        }

        if (!checkAClass.getClassID().equals(newAClass.getAClassID()) && classService.isClassIdExists(newAClass.getAClassID())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseObject("failed", "Mã lớp học đã tồn tại", "")
            );
        }

        if (!checkAClass.getClassName().equals(newAClass.getClassName())) {
            if (classService.isClassNameExists(newAClass.getClassName())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        new ResponseObject("failed", "Tên lớp học đã tồn tại", "")
                );
            }
        }

        AClass updatedAClass = classRepository.findById(id)
                .map(aClass -> {
                    aClass.setTeacher(teacher);
                    aClass.setCourse(course);
                    aClass.setClassName(newAClass.getClassName());
                    aClass.setNumberOfSessions(newAClass.getNumberOfSessions());
                    return classRepository.save(aClass);
                })
                .orElse(null);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Cập nhật lớp học "+ id +" thành công", "")
            );

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteClass (@PathVariable Long id){
        boolean exists = classRepository.existsById(id);
        if(exists){
            classRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("success", "Xoá lớp học có mã: "+ id +" thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Môn lớp không tồn tại!", "")
        );
    }

}
