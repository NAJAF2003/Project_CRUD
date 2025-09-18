package com.CRUD.app.config;

import com.CRUD.app.entity.AppUser;
import com.CRUD.app.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AppUserRepository repo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (repo.count() == 0) {
            repo.save(AppUser.builder()
                    .username("admin")
                    .password(encoder.encode("admin123"))
                    .role("ROLE_USER,ROLE_ADMIN")
                    .build());
        }
    }
}
