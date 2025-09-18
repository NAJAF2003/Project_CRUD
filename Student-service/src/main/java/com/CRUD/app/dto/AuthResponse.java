package com.CRUD.app.dto;

import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthResponse {
    private String accesstoken;
    private String refreshtoken;
}
