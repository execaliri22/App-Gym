package com.example.App_Gym.dto;

import lombok.Data;
import java.util.Set;

@Data
public class RegistroRequest {
    private String dni;
    private String nombreCompleto;
    private String email;
    private String password;
    private String objetivo; // Ejem: "Ganar masa"
    private String sede;
    private Set<String> roles; // Ejem: ["ROLE_SOCIO"]
}