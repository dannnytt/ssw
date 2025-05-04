package com.example.study.lab10.dto;

import com.example.study.lab10.model.entity.Address;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String role;
    private Address address;
}
