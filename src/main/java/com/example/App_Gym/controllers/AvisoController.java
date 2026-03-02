package com.example.App_Gym.controllers;

import com.example.App_Gym.models.Aviso;
import com.example.App_Gym.services.AvisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avisos")
public class AvisoController {

    @Autowired
    private AvisoService avisoService;

    @GetMapping
    public ResponseEntity<List<Aviso>> listar() {
        return ResponseEntity.ok(avisoService.obtenerTodosActivos());
    }

    @PostMapping
    public ResponseEntity<Aviso> guardar(@RequestBody Aviso aviso) {
        return ResponseEntity.ok(avisoService.crear(aviso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable String id, @RequestBody Aviso aviso) {
        try {
            return ResponseEntity.ok(avisoService.actualizar(id, aviso));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrar(@PathVariable String id) {
        try {
            avisoService.eliminar(id);
            return ResponseEntity.ok("Aviso eliminado con éxito");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}