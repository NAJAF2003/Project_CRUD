
/*
package com.CRUD.app.security.OAuth2;

import com.CRUD.app.entity.AppUser;
import com.CRUD.app.repository.AppUserRepository;
import com.CRUD.app.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final AppUserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User customUser = (CustomOAuth2User) authentication.getPrincipal();
        String email = customUser.getEmail();

        // Find or create user in DB
        AppUser user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    AppUser newUser = new AppUser();
                    newUser.setEmail(email);
                    newUser.setUsername(customUser.getName());
                    newUser.setPassword(""); // no password for OAuth2 users
                    newUser.setRole("ROLE_USER");
                    return userRepository.save(newUser);
                });

        // Create claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRole());

        // Generate tokens
        String accessToken = jwtService.generateAccessToken(user, claims);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Write tokens as JSON
        response.setContentType("application/json");
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}


 */