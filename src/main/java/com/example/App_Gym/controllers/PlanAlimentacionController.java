package com.example.App_Gym.controllers;

import com.example.App_Gym.models.PlanAlimentacion;
import com.example.App_Gym.services.PlanAlimentacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alimentacion")
public class PlanAlimentacionController {

    @Autowired
    private PlanAlimentacionService alimentacionService;

    // --- RUTA PARA EL SOCIO ---

    @GetMapping("/mi-dieta")
    public ResponseEntity<?> obtenerMiDieta(Authentication auth) {
        try {
            String email = auth.getName();
            return ResponseEntity.ok(alimentacionService.obtenerPlanDeUsuario(email));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // --- RUTAS PARA EL ADMINISTRADOR ---

    // 1. Crear un plan nutricional
    @PostMapping
    public ResponseEntity<PlanAlimentacion> crear(@RequestBody PlanAlimentacion plan) {
        return ResponseEntity.ok(alimentacionService.guardar(plan));
    }

    // 2. Listar todos los planes
    @GetMapping("/todos")
    public ResponseEntity<List<PlanAlimentacion>> listarTodos() {
        return ResponseEntity.ok(alimentacionService.listarTodos());
    }

    // 3. Editar un plan
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable String id, @RequestBody PlanAlimentacion plan) {
        try {
            return ResponseEntity.ok(alimentacionService.actualizar(id, plan));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 4. Eliminar un plan
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable String id) {
        try {
            alimentacionService.eliminar(id);
            return ResponseEntity.ok("Plan de alimentación eliminado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // 5. Asignar dieta a un socio
    @PostMapping("/asignar")
    public ResponseEntity<String> asignar(
            @RequestParam String email, 
            @RequestParam String planId) {
        try {
            return ResponseEntity.ok(alimentacionService.asignarASocio(email, planId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}