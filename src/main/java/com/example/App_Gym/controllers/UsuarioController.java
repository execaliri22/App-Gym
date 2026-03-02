package com.example.App_Gym.controllers;

import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.repositories.UsuarioRepository;
import com.example.App_Gym.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/perfil")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<?> verMiPerfil(Authentication auth) {
        Usuario usuario = usuarioService.obtenerPerfilPorEmail(auth.getName());
        
        // Si quieres devolver el IMC junto con el usuario, puedes usar un Map
        // O simplemente devolver el usuario y que el Front-end lo calcule.
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> editarPerfil(Authentication auth, @RequestBody Usuario datos) {
        Usuario actualizado = usuarioService.actualizarPerfil(auth.getName(), datos);
        return ResponseEntity.ok(actualizado);
    }
}