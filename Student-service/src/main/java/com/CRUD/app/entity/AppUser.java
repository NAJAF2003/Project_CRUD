package com.CRUD.app.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "users")
@Data   // Lombok annotation - auto generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Slf4j

public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    private String role;

    public String getRoles() {
        return role;
    }


    //Custom Contructor for debugging

    public AppUser(String username, String password, String email) {
        log.debug("Creating new student object");
        this.username=username;
        this.password=password;
        this.email=email;
    }

    //LifeCycle callbacks for debugging
    @PrePersist
    public void prePersist() {
        log.debug("About to save student to database");
    }

    @PostPersist
    public void postPersist() {
        log.debug("student saved to database with id " + this.id);
    }
}
