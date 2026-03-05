package com.example.App_Gym.services;

import com.example.App_Gym.dto.ChatResumenDTO;
import com.example.App_Gym.models.Mensaje;
import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.repositories.MensajeRepository;
import com.example.App_Gym.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private MensajeRepository mensajeRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    private final String ADMIN_EMAIL = "admin@gym.com";

    public Mensaje enviarMensaje(String remitente, String destino, String contenido) {
        Mensaje msj = new Mensaje();
        msj.setRemitenteEmail(remitente);
        msj.setDestinatarioEmail(destino);
        msj.setContenido(contenido);
        return mensajeRepo.save(msj);
    }

    public List<Mensaje> obtenerConversacion(String miEmail, String otroEmail) {
        List<Mensaje> chat = mensajeRepo.findChat(miEmail, otroEmail);
        
        // Marcamos como leídos los mensajes que llegan a quien abre la conversación
        chat.stream()
            .filter(m -> m.getDestinatarioEmail().equals(miEmail) && !m.isLeido())
            .forEach(m -> { 
                m.setLeido(true); 
                mensajeRepo.save(m); 
            });
        return chat;
    }

    // --- LÓGICA DEL DASHBOARD ---
    public List<ChatResumenDTO> obtenerResumenChatsAdmin() {
        List<Mensaje> todos = mensajeRepo.findAll();
        
        // Agrupamos mensajes por el email del socio involucrado
        Map<String, List<Mensaje>> agrupados = todos.stream()
            .filter(m -> m.getRemitenteEmail().equals(ADMIN_EMAIL) || m.getDestinatarioEmail().equals(ADMIN_EMAIL))
            .collect(Collectors.groupingBy(m -> 
                m.getRemitenteEmail().equals(ADMIN_EMAIL) ? m.getDestinatarioEmail() : m.getRemitenteEmail()
            ));

        return agrupados.entrySet().stream().map(entry -> {
            String emailSocio = entry.getKey();
            List<Mensaje> mensajes = entry.getValue();
            
            // Buscamos el nombre para que el admin identifique al socio
            String nombre = usuarioRepo.findByEmail(emailSocio)
                    .map(Usuario::getNombreCompleto)
                    .orElse("Usuario Desconocido");

            // Obtenemos el último mensaje para la previsualización
            mensajes.sort(Comparator.comparing(Mensaje::getFechaEnvio).reversed());
            Mensaje ultimo = mensajes.get(0);
            
            // Contamos cuántos envió el socio que el admin no ha leído
            long sinLeer = mensajes.stream()
                .filter(m -> m.getDestinatarioEmail().equals(ADMIN_EMAIL) && !m.isLeido())
                .count();
                
            return new ChatResumenDTO(emailSocio, nombre, ultimo.getContenido(), ultimo.getFechaEnvio(), sinLeer);
        })
        .sorted(Comparator.comparing(ChatResumenDTO::getFechaUltimoMensaje).reversed())
        .collect(Collectors.toList());
    }
}