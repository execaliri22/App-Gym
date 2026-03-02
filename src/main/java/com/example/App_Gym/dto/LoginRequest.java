package com.example.App_Gym.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}