package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Set;

@Data
@Document(collection = "usuarios")
public class Usuario {
    @Id
    private String id;
    
    @Indexed(unique = true)
    private String dni;
    
    private String nombreCompleto;
    
    @Indexed(unique = true)
    private String email;
    
    private String password; 
    
    // --- NUEVOS CAMPOS DE PERFIL ---
    private String fotoPerfilUrl; 
    private String descripcion;   // Breve biografía o motivación
    private Double peso;          // En kg (ej: 75.5)
    private Integer altura;       // En cm (ej: 178)
    private String objetivo;      // Ej: "Perder peso"
    // -------------------------------

    private String sede;
    private boolean estadoSocio = false;
    private Set<Rol> roles; 
    
    private String planEntrenamientoId;
    private String planAlimentacionId;
}