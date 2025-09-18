package com.CRUD.app.controller;

import com.CRUD.app.dto.AuthResponse;
import com.CRUD.app.dto.LoginRequest;
import com.CRUD.app.dto.RegisterRequest;
import com.CRUD.app.entity.AppUser;
import com.CRUD.app.repository.AppUserRepository;
import com.CRUD.app.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    // ------------------- REGISTER -------------------
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            return "Username already taken";
        }
        String roles = (req.getRoles() == null || req.getRoles().isBlank()) ? "ROLE_USER" : req.getRoles();
        var user = AppUser.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(roles)
                .build();
        userRepo.save(user);
        return "Registered";
    }

    // ------------------- LOGIN -------------------
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));

        UserDetails user = userDetailsService.loadUserByUsername(req.getUsername());

        // Extract roles from authorities
        List<String> roles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // Create claims map
        Map<String, Object> claims = Map.of("roles", roles);

        // Generate access + refresh tokens
        String accessToken = jwtService.generateAccessToken(claims, user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Return both in response
        return new AuthResponse(accessToken, refreshToken);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        // Extract username from refresh token
        String username = jwtService.extractUsername(refreshToken);
        var user = userDetailsService.loadUserByUsername(username);

        // Validate refresh token
        if (jwtService.isTokenValid(refreshToken, user)) {
            List<String> roles = user.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            // Generate a new access token
            String newAccessToken = jwtService.generateAccessToken(
                    Map.of("roles", roles),
                    user
            );

            // Optionally: generate a new refresh token too
            String newRefreshToken = jwtService.generateRefreshToken(user);

            return new AuthResponse(newAccessToken, newRefreshToken);
        } else {
            throw new RuntimeException("Invalid refresh token");
        }
    }

}