package com.example.study.AuthService.service;

import com.example.study.AuthService.domain.model.User;
import com.example.study.AuthService.dto.UserDto;
import com.example.study.AuthService.dto.request.AuthRequest;
import com.example.study.AuthService.dto.request.RegisterRequest;
import com.example.study.AuthService.dto.response.AuthResponse;
import com.example.study.AuthService.dto.response.RegisterErrorResponse;
import com.example.study.AuthService.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthService {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(
            CustomUserDetailsService userDetailsService,
            JwtTokenProvider jwtTokenProvider,
            AuthenticationManager authenticationManager) {

        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManager = authenticationManager;
    }

    public ResponseEntity<AuthResponse> createAuthToken(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtTokenProvider.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(
                new AuthResponse(token)
        );
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegisterRequest request) {
        if(!request.getPassword().equals(request.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new RegisterErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            "Пароли не совпадают"
                    )
            );
        }

        if (userDetailsService.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new RegisterErrorResponse(
                            HttpStatus.BAD_REQUEST.value(),
                            "Пользователь с указанным именем существует"
                    )
            );
        }

        User user = userDetailsService.createNewUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(
                new UserDto(
                        user.getUsername(),
                        user.getRole().name()
                )
        );
    }
}
