package com.CRUD.app.service;

import com.CRUD.app.dto.StudentRequestDto;
import com.CRUD.app.entity.Student;
import com.CRUD.app.repository.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class StudentService {

    private final StudentRepository studentRepository;  // final since it's injected via constructor

    // ✅ Constructor injection (Spring will auto-wire this)
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
        log.info("StudentService bean is created and injected with StudentRepository");
    }

    public Student createStudent(StudentRequestDto dto) {
        Student student = new Student();
        student.setStudentName(dto.getStudentName());
        student.setMarks(dto.getMarks());
        student.setAddress(dto.getAddress());
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(int studentId) {
        return studentRepository.findById(studentId).orElseThrow(
                () -> new RuntimeException("Student not found with id " + studentId)
        );
    }

    @Transactional
    public Student updateStudent(StudentRequestDto dto, int studentId) {
        Student s = studentRepository.findById(studentId).orElseThrow(
                () -> new RuntimeException("Student not found with id " + studentId)
        );
        s.setStudentName(dto.getStudentName());
        s.setAddress(dto.getAddress());
        s.setMarks(dto.getMarks());
        return studentRepository.save(s);
    }

    public String deleteStudentById(int studentId) {
        studentRepository.deleteById(studentId);
        return "Student Deleted Successfully";
    }
}
