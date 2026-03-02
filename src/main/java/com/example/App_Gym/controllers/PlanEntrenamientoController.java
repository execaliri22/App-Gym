package com.example.App_Gym.controllers;

import com.example.App_Gym.models.PlanEntrenamiento;
import com.example.App_Gym.services.PlanEntrenamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planes")
public class PlanEntrenamientoController {

    @Autowired
    private PlanEntrenamientoService planService;

    // --- SECCIÓN PARA EL SOCIO ---

    @GetMapping("/mi-plan")
    public ResponseEntity<?> obtenerMiPlan(Authentication authentication) {
        try {
            // Extrae el email del token JWT del usuario logueado
            String email = authentication.getName();
            PlanEntrenamiento plan = planService.obtenerPlanDeUsuario(email);
            return ResponseEntity.ok(plan);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // --- SECCIÓN PARA EL ADMINISTRADOR ---

    // 1. Listar todos los planes maestros
    @GetMapping("/todos")
    public ResponseEntity<List<PlanEntrenamiento>> listarTodo() {
        return ResponseEntity.ok(planService.listarTodos());
    }

    // 2. Crear un nuevo plan maestro
    @PostMapping
    public ResponseEntity<PlanEntrenamiento> crear(@RequestBody PlanEntrenamiento plan) {
        return ResponseEntity.ok(planService.guardar(plan));
    }

    // 3. Editar un plan existente
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable String id, @RequestBody PlanEntrenamiento plan) {
        try {
            return ResponseEntity.ok(planService.actualizar(id, plan));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 4. Eliminar un plan
    @DeleteMapping("/{id}")
    public ResponseEntity<String> borrar(@PathVariable String id) {
        try {
            planService.eliminar(id);
            return ResponseEntity.ok("Plan eliminado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 5. ASIGNAR un plan a un socio específico
    @PostMapping("/asignar")
    public ResponseEntity<String> asignarPlan(
            @RequestParam String email, 
            @RequestParam String planId) {
        try {
            String resultado = planService.asignarPlanAUsuario(email, planId);
            return ResponseEntity.ok(resultado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}