package com.CRUD.app;

import com.CRUD.app.entity.Student;
import com.CRUD.app.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudentRepositoryTests {

    private final StudentRepository studentRepository;

    // ✅ Constructor-based DI
    @Autowired
    public StudentRepositoryTests(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Test
    public void saveStudentTest() {
        Student student = Student.builder()
                .studentName("Ali")
                .address("Delhi")
                .marks(85)
                .build();

        Student saved = studentRepository.save(student);

        Assertions.assertTrue(saved.getStudentId() > 0);
    }

    @Test
    public void getStudentTest() {
        Student student = studentRepository.findById(1).get();
        Assertions.assertEquals(1, student.getStudentId());
    }

    @Test
    public void getAllStudentTest() {
        List<Student> students = studentRepository.findAll();
        Assertions.assertTrue(students.size() > 0);
    }

    @Test
    public void updateStudentTest() {
        Student student = studentRepository.findById(1).get();
        student.setStudentName("Afzal Kazmi");
        Student studentUpdated = studentRepository.save(student);
        Assertions.assertEquals("Afzal Kazmi", studentUpdated.getStudentName()); // ✅ fixed string comparison
    }
}
