package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "avisos")
public class Aviso {
    @Id
    private String id;
    private String titulo;
    private String descripcion;
    private String imagenUrl; // Aquí guardaremos la URL o el Base64 de la foto
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private boolean activo = true;
}