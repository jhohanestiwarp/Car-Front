package com.example.car_back.auth.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
