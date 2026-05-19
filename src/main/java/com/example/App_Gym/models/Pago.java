package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;
import java.time.LocalDateTime;

@Data
@Document(collection = "pagos")
public class Pago {
    @Id
    private String id;
    private String usuarioId;        // Referencia al ID del Usuario
    private Double monto;            // Monto del abono
    private LocalDateTime fechaPago; // Fecha en la que se realizó el cobro
    private LocalDateTime fechaVencimiento; // Cuándo vence la cuota
    private String metodoPago;       // Ej: "Efectivo", "Tarjeta"
    private String estado;           // "PAGADO", "PENDIENTE", "VENCIDO"

    @Transient
    private String nombreUsuario;

    @Transient
    private String dniUsuario;

    @Transient
    private String emailUsuario;
}