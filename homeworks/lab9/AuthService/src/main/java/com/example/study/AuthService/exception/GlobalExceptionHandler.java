package com.example.study.AuthService.exception;

import com.example.study.AuthService.dto.response.AuthErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AuthErrorResponse> handleUserNotFound(UsernameNotFoundException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Пользователь не найден"
                ));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<AuthErrorResponse> handleBadCredentials(BadCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new AuthErrorResponse(
                        HttpStatus.UNAUTHORIZED.value(),
                        "Неправильный логин или пароль"
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<AuthErrorResponse> handleAllExceptions(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new AuthErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Ошибка сервера: " + ex.getMessage()
                ));
    }
}
