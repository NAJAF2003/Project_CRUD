package com.CRUD.app.controller;

import com.CRUD.app.dto.StudentRequestDto;
import com.CRUD.app.entity.Student;
import com.CRUD.app.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection (Spring will automatically inject StudentService)
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
        log.info("StudentController bean is created and injected with StudentService");
    }

    @PostMapping("/create")
    public Student createStudent(@RequestBody StudentRequestDto dto) {
        return studentService.createStudent(dto);
    }

    @GetMapping("/all")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable int studentId) {
        return studentService.getStudentById(studentId);
    }

    @PutMapping("/{studentId}")
    public Student updateStudent(@RequestBody StudentRequestDto dto, @PathVariable int studentId) {
        return studentService.updateStudent(dto, studentId);
    }

    // Only ADMINs can delete
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{studentId}")
    public String deletedStudentById(@PathVariable int studentId) {
        return studentService.deleteStudentById(studentId);
    }
}
