package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "mensajes")
public class Mensaje {
    @Id
    private String id;
    private String remitenteEmail;    // Email de quien envía
    private String destinatarioEmail; // Email de quien recibe
    private String contenido;
    private LocalDateTime fechaEnvio = LocalDateTime.now();
    private boolean leido = false;
}
