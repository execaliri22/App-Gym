package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "planes_alimentacion")
public class PlanAlimentacion {
    @Id
    private String id;
    private String nombrePlan;        // Ej: "Dieta Cetogénica"
    private String objetivoPlan;      // Ej: "Definición muscular"
    private String descripcion;       // Notas generales sobre el plan
    
    // La "Tabla" organizada por días
    private List<DiaAlimentacion> dias;
}

@Data
class DiaAlimentacion {
    private String nombreDia;         // Ej: "Lunes"
    private List<DetalleComida> comidas;
}

@Data
class DetalleComida {
    private String hora;              // Ej: "08:30 AM"
    private String tipoComida;        // Ej: "Desayuno", "Snack", "Almuerzo"
    private String nombrePlato;       // Ej: "Omelette de espinacas"
    private String ingredientes;      // Ej: "3 huevos, 50g espinaca, aceite de oliva"
    private String observaciones;     // Ej: "No usar sal en exceso"
}