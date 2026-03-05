package com.example.App_Gym.controllers;

import com.example.App_Gym.dto.ChatResumenDTO;
import com.example.App_Gym.models.Mensaje;
import com.example.App_Gym.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    // --- SECCIÓN SOCIO (Tus rutas actuales) ---
    @GetMapping("/mi-admin")
    public ResponseEntity<List<Mensaje>> verChatConAdmin(Authentication auth) {
        return ResponseEntity.ok(chatService.obtenerConversacion(auth.getName(), "admin@gym.com"));
    }

    @PostMapping("/enviar-a-admin")
    public ResponseEntity<Mensaje> enviarAdmin(Authentication auth, @RequestBody Mensaje mensajeRequest) {
        return ResponseEntity.ok(chatService.enviarMensaje(auth.getName(), "admin@gym.com", mensajeRequest.getContenido()));
    }

    // --- SECCIÓN ADMIN ---

    // NUEVO: Dashboard para ver todos los contactos y mensajes pendientes
    @GetMapping("/admin/dashboard")
    public ResponseEntity<List<ChatResumenDTO>> verDashboard(Authentication auth) {
        return ResponseEntity.ok(chatService.obtenerResumenChatsAdmin());
    }

    @GetMapping("/con-socio/{emailSocio}")
    public ResponseEntity<List<Mensaje>> verChatConSocio(Authentication auth, @PathVariable String emailSocio) {
        return ResponseEntity.ok(chatService.obtenerConversacion(auth.getName(), emailSocio));
    }

    @PostMapping("/responder/{emailSocio}")
    public ResponseEntity<Mensaje> responder(Authentication auth, @PathVariable String emailSocio, @RequestBody Mensaje mensajeRequest) {
        return ResponseEntity.ok(chatService.enviarMensaje(auth.getName(), emailSocio, mensajeRequest.getContenido()));
    }
}