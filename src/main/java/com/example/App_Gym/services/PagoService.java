package com.example.App_Gym.services;

import com.example.App_Gym.models.Pago;
import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.repositories.PagoRepository;
import com.example.App_Gym.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public List<Pago> obtenerHistorialSocio(String email) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Pago> pagos = pagoRepo.findByUsuarioIdOrderByFechaVencimientoDesc(usuario.getId());

        if (pagos != null) {
            pagos.forEach(pago -> {
                if (pago.getFechaVencimiento() != null && pago.getFechaVencimiento().isBefore(LocalDateTime.now())) {
                    pago.setEstado("VENCIDO"); 
                }
            });
        }

        return pagos;
    }

    public List<Pago> obtenerTodosLosPagos() {
        List<Pago> todosLosPagos = pagoRepo.findAll();
        
        if (todosLosPagos == null || todosLosPagos.isEmpty()) {
            return todosLosPagos;
        }
        
        todosLosPagos.forEach(pago -> {
            try {
                if (pago.getFechaVencimiento() != null && pago.getFechaVencimiento().isBefore(LocalDateTime.now())) {
                    pago.setEstado("VENCIDO");
                }

                if (pago.getUsuarioId() != null && !pago.getUsuarioId().trim().isEmpty()) {
                    usuarioRepo.findById(pago.getUsuarioId()).ifPresentOrElse(
                        usuario -> {
                            pago.setNombreUsuario(usuario.getNombreCompleto() != null ? usuario.getNombreCompleto() : "Sin Nombre Registrado");
                            pago.setDniUsuario(usuario.getDni() != null ? usuario.getDni() : "-");
                            pago.setEmailUsuario(usuario.getEmail() != null ? usuario.getEmail() : "-");
                        },
                        () -> {
                            pago.setNombreUsuario("Usuario Eliminado");
                            pago.setDniUsuario("-");
                            pago.setEmailUsuario("-");
                        }
                    );
                } else {
                    pago.setNombreUsuario("No Asignado");
                    pago.setDniUsuario("-");
                    pago.setEmailUsuario("-");
                }
            } catch (Exception e) {
                System.err.println("Error procesando columnas del pago: " + pago.getId() + " -> " + e.getMessage());
                pago.setNombreUsuario("Error de carga");
                pago.setDniUsuario("-");
                pago.setEmailUsuario("-");
            }
        });
        
        return todosLosPagos;
    }
}