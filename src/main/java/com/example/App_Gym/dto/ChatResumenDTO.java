package com.example.App_Gym.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ChatResumenDTO {
    private String emailSocio;
    private String nombreSocio;
    private String ultimoMensaje;
    private LocalDateTime fechaUltimoMensaje;
    private long mensajesSinLeer;
}