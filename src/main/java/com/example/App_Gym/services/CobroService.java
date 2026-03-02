package com.example.App_Gym.services;

import com.example.App_Gym.models.Pago;
import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.models.Aviso;
import com.example.App_Gym.repositories.PagoRepository;
import com.example.App_Gym.repositories.UsuarioRepository;
import com.example.App_Gym.repositories.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class CobroService {

    @Autowired
    private PagoRepository pagoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AvisoRepository avisoRepo; // Para generar las notificaciones

    public Pago registrarCobroPorDni(String dni, Double monto, String metodo) {
        Usuario usuario = usuarioRepo.findByDni(dni)
                .orElseThrow(() -> new RuntimeException("No se encontró ningún socio con el DNI: " + dni));

        Pago nuevoCobro = new Pago();
        nuevoCobro.setUsuarioId(usuario.getId());
        nuevoCobro.setMonto(monto);
        nuevoCobro.setMetodoPago(metodo);
        nuevoCobro.setFechaPago(LocalDateTime.now());
        nuevoCobro.setEstado("PAGADO");
        nuevoCobro.setFechaVencimiento(LocalDateTime.now().plusDays(30));

        Pago guardado = pagoRepo.save(nuevoCobro);

        // --- GENERAR NOTIFICACIÓN AUTOMÁTICA ---
        Aviso notificacion = new Aviso();
        notificacion.setTitulo("Confirmación de Pago");
        notificacion.setDescripcion("Hola " + usuario.getNombreCompleto() + ", hemos registrado tu pago de $" + monto + " vía " + metodo + ". Tu suscripción está activa.");
        notificacion.setActivo(true);
        avisoRepo.save(notificacion);

        return guardado;
    }

    @SuppressWarnings("unused")
    public void eliminarCobro(String id) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobro no encontrado"));
        
        Usuario usuario = usuarioRepo.findById(pago.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        pagoRepo.deleteById(id);

        // --- GENERAR NOTIFICACIÓN DE ANULACIÓN ---
        Aviso anulacion = new Aviso();
        anulacion.setTitulo("Pago Anulado");
        anulacion.setDescripcion("Se ha anulado un registro de pago previo por un monto de $" + pago.getMonto() + ". Por favor, contacta con administración.");
        anulacion.setActivo(true);
        avisoRepo.save(anulacion);
    }

    public Pago editarCobro(String id, Double nuevoMonto, String nuevoMetodo) {
        Pago pago = pagoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cobro no encontrado"));

        if (nuevoMonto != null) pago.setMonto(nuevoMonto);
        if (nuevoMetodo != null) pago.setMetodoPago(nuevoMetodo);
        
        Pago actualizado = pagoRepo.save(pago);

        // Notificación de corrección
        Aviso correccion = new Aviso();
        correccion.setTitulo("Actualización de Pago");
        correccion.setDescripcion("Se han corregido los detalles de tu último pago. Monto actual: $" + actualizado.getMonto());
        correccion.setActivo(true);
        avisoRepo.save(correccion);

        return actualizado;
    }
}