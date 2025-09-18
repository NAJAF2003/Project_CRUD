package com.CRUD.app;

import com.CRUD.app.controller.StudentController;
import com.CRUD.app.dto.StudentRequestDto;
import com.CRUD.app.entity.Student;
import com.CRUD.app.security.JwtAuthFilter;
import com.CRUD.app.security.JwtService;
import com.CRUD.app.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = StudentController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security filters
public class StudentControllerTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @MockitoBean
    private StudentService studentService;

    @MockitoBean
    private JwtAuthFilter jwtAuthFilter;

    @MockitoBean
    private JwtService jwtService;

    // ✅ Constructor-based DI
    @Autowired
    public StudentControllerTests(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    @Test
    void testCreateStudent() throws Exception {
        Student student = new Student(1, "John Doe", "New York", 85);
        StudentRequestDto dto = new StudentRequestDto("John Doe", "New York", 85);

        Mockito.when(studentService.createStudent(any(StudentRequestDto.class))).thenReturn(student);

        mockMvc.perform(post("/students/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(1))
                .andExpect(jsonPath("$.studentName").value("John Doe"))
                .andExpect(jsonPath("$.address").value("New York"))
                .andExpect(jsonPath("$.marks").value(85));
    }

    @Test
    void testGetStudentById() throws Exception {
        Student student = new Student(2, "Jane Doe", "London", 90);

        Mockito.when(studentService.getStudentById(2)).thenReturn(student);

        mockMvc.perform(get("/students/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.studentId").value(2))
                .andExpect(jsonPath("$.studentName").value("Jane Doe"))
                .andExpect(jsonPath("$.address").value("London"))
                .andExpect(jsonPath("$.marks").value(90));
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<Student> students = Arrays.asList(
                new Student(1, "Alice", "Paris", 88),
                new Student(2, "Bob", "Berlin", 92)
        );

        Mockito.when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/students/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].studentName").value("Alice"))
                .andExpect(jsonPath("$[1].studentName").value("Bob"));
    }

    @Test
    void testUpdateStudent() throws Exception {
        Student updatedStudent = new Student(3, "Charlie", "Rome", 75);
        StudentRequestDto dto = new StudentRequestDto("Charlie", "Rome", 75);

        Mockito.when(studentService.updateStudent(eq(dto), eq(3))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/3")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.studentId").value(3))
                .andExpect(jsonPath("$.studentName").value("Charlie"))
                .andExpect(jsonPath("$.address").value("Rome"))
                .andExpect(jsonPath("$.marks").value(75));
    }
}
