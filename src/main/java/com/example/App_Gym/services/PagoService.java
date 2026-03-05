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

        // Lógica de verificación de estado en tiempo real
        pagos.forEach(pago -> {
            if (pago.getFechaVencimiento().isBefore(LocalDateTime.now()) && !"PAGADO".equals(pago.getEstado())) {
                pago.setEstado("VENCIDO");
            }
        });

        return pagos;
    }
}