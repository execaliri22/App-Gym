package com.example.App_Gym.services;

import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    public Usuario obtenerPerfilPorEmail(String email) {
        return usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }

    public Usuario actualizarPerfil(String email, Usuario datosNuevos) {
        Usuario usuario = obtenerPerfilPorEmail(email);

        // Actualizamos los campos de perfil
        if (datosNuevos.getNombreCompleto() != null) usuario.setNombreCompleto(datosNuevos.getNombreCompleto());
        if (datosNuevos.getFotoPerfilUrl() != null) usuario.setFotoPerfilUrl(datosNuevos.getFotoPerfilUrl());
        if (datosNuevos.getDescripcion() != null) usuario.setDescripcion(datosNuevos.getDescripcion());
        if (datosNuevos.getPeso() != null) usuario.setPeso(datosNuevos.getPeso());
        if (datosNuevos.getAltura() != null) usuario.setAltura(datosNuevos.getAltura());
        if (datosNuevos.getObjetivo() != null) usuario.setObjetivo(datosNuevos.getObjetivo());

        return usuarioRepo.save(usuario);
    }

    // Método extra: Calcular IMC
    public Double calcularIMC(Usuario usuario) {
        if (usuario.getPeso() == null || usuario.getAltura() == null || usuario.getAltura() == 0) {
            return 0.0;
        }
        // Fórmula: peso / (altura_en_metros ^ 2)
        double alturaMetros = usuario.getAltura() / 100.0;
        double imc = usuario.getPeso() / (alturaMetros * alturaMetros);
        
        // Redondear a 2 decimales
        return Math.round(imc * 100.0) / 100.0;
    }
}