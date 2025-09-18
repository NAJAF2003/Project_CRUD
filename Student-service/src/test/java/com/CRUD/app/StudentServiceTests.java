package com.CRUD.app;

import com.CRUD.app.dto.StudentRequestDto;
import com.CRUD.app.entity.Student;
import com.CRUD.app.repository.StudentRepository;
import com.CRUD.app.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StudentServiceTests {

    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // ✅ Constructor injection: manually inject the mocked repo
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void createStudentTest() {
        StudentRequestDto dto = new StudentRequestDto("Ali", "Delhi", 85);
        Student student = new Student(1, "Ali", "Delhi", 85);

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student saved = studentService.createStudent(dto);

        assertThat(saved).isNotNull();
        assertThat(saved.getStudentId()).isEqualTo(1);
        assertThat(saved.getStudentName()).isEqualTo("Ali");
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void getStudentByIdTest() {
        Student student = new Student(1, "Ali", "Delhi", 85);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        Student found = studentService.getStudentById(1);

        assertThat(found).isNotNull();
        assertThat(found.getStudentId()).isEqualTo(1);
        assertThat(found.getStudentName()).isEqualTo("Ali");
        verify(studentRepository, times(1)).findById(1);
    }

    @Test
    public void getAllStudentsTest() {
        List<Student> students = Arrays.asList(
                new Student(1, "Ali", "Delhi", 85),
                new Student(2, "Kazmi", "Mumbai", 90)
        );

        when(studentRepository.findAll()).thenReturn(students);

        List<Student> result = studentService.getAllStudents();

        assertThat(result).isNotEmpty();
        assertThat(result.size()).isEqualTo(2);
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void updateStudentTest() {
        StudentRequestDto dto = new StudentRequestDto("Updated", "Delhi", 95);
        Student student = new Student(1, "Ali", "Delhi", 85);

        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
        when(studentRepository.save(any(Student.class))).thenReturn(
                new Student(1, "Updated", "Delhi", 95)
        );

        Student updated = studentService.updateStudent(dto, 1);

        assertThat(updated.getStudentName()).isEqualTo("Updated");
        assertThat(updated.getMarks()).isEqualTo(95);
        verify(studentRepository, times(1)).findById(1);
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    public void deleteStudentByIdTest() {
        doNothing().when(studentRepository).deleteById(1);

        String result = studentService.deleteStudentById(1);

        assertThat(result).isEqualTo("Student Deleted Successfully");
        verify(studentRepository, times(1)).deleteById(1);
    }
}
