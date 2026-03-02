package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String usuarioId; // Referencia al ID del Usuario
    private Double monto;
    private LocalDateTime fechaPago;
    private String metodoPago;
}
