package com.example.App_Gym.controllers;

import com.example.App_Gym.models.Pago;
import com.example.App_Gym.services.CobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/cobros")
public class CobroController {

    @Autowired
    private CobroService cobroService;

    // POST: Registrar cobro
    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestParam String dni, 
                                     @RequestParam Double monto, 
                                     @RequestParam String metodo) {
        try {
            return ResponseEntity.ok(cobroService.registrarCobroPorDni(dni, monto, metodo));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT: Editar cobro existente
    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editar(@PathVariable String id, 
                                   @RequestParam(required = false) Double monto, 
                                   @RequestParam(required = false) String metodo) {
        try {
            return ResponseEntity.ok(cobroService.editarCobro(id, monto, metodo));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // DELETE: Eliminar un cobro
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        try {
            cobroService.eliminarCobro(id);
            return ResponseEntity.ok("Cobro eliminado correctamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}