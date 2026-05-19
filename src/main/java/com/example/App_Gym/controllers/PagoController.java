package com.example.App_Gym.controllers;

import com.example.App_Gym.models.Pago;
import com.example.App_Gym.services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    // Endpoint para que el socio vea sus pagos pagados, pendientes y vencidos
    @GetMapping("/mis-pagos")
    public ResponseEntity<List<Pago>> verMisPagos(Authentication auth) {
        // auth.getName() obtiene el email del token JWT
        return ResponseEntity.ok(pagoService.obtenerHistorialSocio(auth.getName()));
    }
    @GetMapping("/todos")
    public ResponseEntity<List<Pago>> verTodosLosPagos() {
        return ResponseEntity.ok(pagoService.obtenerTodosLosPagos());
    }
}