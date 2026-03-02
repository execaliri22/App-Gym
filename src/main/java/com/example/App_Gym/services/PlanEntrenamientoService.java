package com.example.App_Gym.services;

import com.example.App_Gym.models.PlanEntrenamiento;
import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.repositories.PlanEntrenamientoRepository;
import com.example.App_Gym.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanEntrenamientoService {

    @Autowired
    private PlanEntrenamientoRepository planRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    // --- MÉTODOS CRUD ---

    public PlanEntrenamiento guardar(PlanEntrenamiento plan) {
        return planRepo.save(plan);
    }

    public List<PlanEntrenamiento> listarTodos() {
        return planRepo.findAll();
    }

    public PlanEntrenamiento actualizar(String id, PlanEntrenamiento datosNuevos) {
        PlanEntrenamiento plan = planRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado"));
        
        plan.setNombrePlan(datosNuevos.getNombrePlan());
        plan.setObjetivoPlan(datosNuevos.getObjetivoPlan());
        plan.setDescripcion(datosNuevos.getDescripcion());
        plan.setDuracionSemanas(datosNuevos.getDuracionSemanas());
        plan.setDias(datosNuevos.getDias());
        
        return planRepo.save(plan);
    }

    public void eliminar(String id) {
        planRepo.deleteById(id);
    }

    // --- MÉTODOS DE ASIGNACIÓN Y CONSULTA ---

    // ESTE ES EL MÉTODO QUE TE FALTABA O ESTABA MAL NOMBRADO
    public String asignarPlanAUsuario(String email, String planId) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        
        PlanEntrenamiento plan = planRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado con ID: " + planId));

        usuario.setPlanEntrenamientoId(plan.getId()); 
        usuarioRepo.save(usuario);
        
        return "Plan '" + plan.getNombrePlan() + "' asignado a " + usuario.getNombreCompleto();
    }

    public PlanEntrenamiento obtenerPlanDeUsuario(String email) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (usuario.getPlanEntrenamientoId() == null || usuario.getPlanEntrenamientoId().isEmpty()) {
            throw new RuntimeException("El socio aún no tiene un plan asignado");
        }

        return planRepo.findById(usuario.getPlanEntrenamientoId())
                .orElseThrow(() -> new RuntimeException("El plan asignado ya no existe en la base de datos"));
    }
}