package com.example.study.lab10.controller;

import com.example.study.lab10.dto.*;
import com.example.study.lab10.service.CustomerService;
import com.example.study.lab10.service.KeycloakService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final CustomerService customerService;
    private final KeycloakService keycloakService;

    @PostMapping("/register")
    public ResponseEntity<SuccessResponse> registerUser(@RequestBody RegisterRequest request) {
        String token = keycloakService.getAdminToken();
        String keycloakId = keycloakService.createUser(request, token);
        customerService.createCustomer(request, keycloakId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SuccessResponse(200, "You have been successfully registered!"));
    }

    @PostMapping("/login")
    public ResponseEntity<SessionResponse> authorizeUser(@RequestBody LoginRequest request) {
        SessionResponse session = keycloakService.authenticateUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(session);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> deauthorizeUser(@RequestBody LogoutRequest dto) {
        keycloakService.terminate(dto.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
