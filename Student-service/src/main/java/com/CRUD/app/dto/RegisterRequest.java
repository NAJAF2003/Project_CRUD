package com.CRUD.app.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String roles; // e.g. "ROLE_USER" or "ROLE_USER,ROLE_ADMIN"
}
