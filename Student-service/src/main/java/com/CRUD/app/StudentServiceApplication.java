package com.CRUD.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class StudentServiceApplication {

	public static void main(String[] args) {

        log.info("Student Service application starts");
        SpringApplication.run(StudentServiceApplication.class, args);
	}

}
