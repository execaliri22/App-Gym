package com.example.App_Gym.services;

import com.example.App_Gym.config.JwtUtil;
import com.example.App_Gym.dto.*;
import com.example.App_Gym.models.*;
import com.example.App_Gym.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@SuppressWarnings("unused")
@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registro público: Cualquier persona que se registre por aquí 
     * SIEMPRE será ROLE_SOCIO por seguridad.
     */
    public String registrarSocio(RegistroRequest req) {
        if (usuarioRepo.findByEmail(req.getEmail()).isPresent()) {
            return "Error: El email ya existe";
        }
        
        if (usuarioRepo.findByDni(req.getDni()).isPresent()) {
            return "Error: El DNI ya existe";
        }
        
        Usuario socio = new Usuario();
        socio.setDni(req.getDni());
        socio.setNombreCompleto(req.getNombreCompleto());
        socio.setEmail(req.getEmail());
        socio.setPassword(passwordEncoder.encode(req.getPassword()));
        socio.setObjetivo(req.getObjetivo());
        socio.setSede(req.getSede());
        
        // Forzamos que sea SOCIO. Eliminamos la lectura de roles desde el request
        // para evitar que alguien se auto-asigne ROLE_ADMIN por Postman.
        socio.setRoles(Collections.singleton(Rol.ROLE_SOCIO));
        
        usuarioRepo.save(socio);
        return "Socio registrado con éxito";
    }

    public AuthResponse login(LoginRequest req) {
        Usuario user = usuarioRepo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail());
            // Enviamos el nombre completo para el frontend
            return new AuthResponse(token, user.getEmail(), user.getNombreCompleto());
        } else {
            throw new RuntimeException("Contraseña incorrecta");
        }
    }

   public void crearAdministradorSiNoExiste() {
        String emailAdmin = "admin@gym.com";
        Optional<Usuario> adminOpt = usuarioRepo.findByEmail(emailAdmin);
        
        if (adminOpt.isEmpty()) {
            Usuario admin = new Usuario();
            admin.setEmail(emailAdmin);
            admin.setNombreCompleto("Administrador del Gym");
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña fija: admin123
            admin.setSede("Sede Central");
            admin.setObjetivo("Administración");
            admin.setDni("00000000");
            
            admin.setEstadoSocio(true); 
            
            admin.setRoles(Collections.singleton(Rol.ROLE_ADMIN));
            
            usuarioRepo.save(admin);
            System.out.println("====== ADMINISTRADOR CREADO CON ÉXITO (admin@gym.com / admin123) ======");
        } else {
            Usuario admin = adminOpt.get();
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRoles(Collections.singleton(Rol.ROLE_ADMIN));
            admin.setEstadoSocio(true); 
            usuarioRepo.save(admin);
            System.out.println("====== CONTRASEÑA DE ADMINISTRADOR RESTABLECIDA A 'admin123' ======");
        }
    }
}