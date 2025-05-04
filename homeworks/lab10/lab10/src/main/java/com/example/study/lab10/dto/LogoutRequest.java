package com.example.study.lab10.dto;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
