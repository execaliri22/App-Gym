package com.example.App_Gym.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "rutinas")
public class PlanEntrenamiento {
    @Id
    private String id;
    private String nombrePlan;      // Ej: "Hipertrofia Avanzada"
    private String objetivoPlan;    // Ej: "Ganar masa muscular"
    private String descripcion;     // Descripción general del plan
    private String duracionSemanas;
    
    // Aquí es donde vive la "Tabla" de entrenamiento
    private List<DiaEntrenamiento> dias;
}

@Data
class DiaEntrenamiento {
    private String nombreDia;        // Ej: "Lunes" o "Día 1"
    private String seccionMuscular;  // Ej: "Pecho y Tríceps"
    private List<DetalleEjercicio> ejercicios;
}

@Data
class DetalleEjercicio {
    private String nombreEjercicio;  // Ej: "Press de Banca"
    private int series;
    private String repeticiones;     // String para permitir "12-10-8" o "Al fallo"
    private String observaciones;    // Ej: "Descanso de 60 seg"
}