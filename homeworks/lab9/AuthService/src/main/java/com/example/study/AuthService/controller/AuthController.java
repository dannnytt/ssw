package com.example.study.AuthService.controller;

import com.example.study.AuthService.dto.request.AuthRequest;
import com.example.study.AuthService.dto.response.AuthResponse;
import com.example.study.AuthService.dto.request.RegisterRequest;
import com.example.study.AuthService.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
        return authService.createAuthToken(request);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createNewUser(@RequestBody RegisterRequest request) {
        return authService.createNewUser(request);
    }
}
