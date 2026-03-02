package com.example.App_Gym.services;

import com.example.App_Gym.models.PlanAlimentacion;
import com.example.App_Gym.models.Usuario;
import com.example.App_Gym.repositories.PlanAlimentacionRepository;
import com.example.App_Gym.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PlanAlimentacionService {

    @Autowired
    private PlanAlimentacionRepository planAlimentacionRepo;

    @Autowired
    private UsuarioRepository usuarioRepo;

    public PlanAlimentacion guardar(PlanAlimentacion plan) {
        return planAlimentacionRepo.save(plan);
    }

    public List<PlanAlimentacion> listarTodos() {
        return planAlimentacionRepo.findAll();
    }

    public PlanAlimentacion actualizar(String id, PlanAlimentacion nuevosDatos) {
        PlanAlimentacion plan = planAlimentacionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan de alimentación no encontrado"));
        
        plan.setNombrePlan(nuevosDatos.getNombrePlan());
        plan.setObjetivoPlan(nuevosDatos.getObjetivoPlan());
        plan.setDescripcion(nuevosDatos.getDescripcion());
        plan.setDias(nuevosDatos.getDias());
        
        return planAlimentacionRepo.save(plan);
    }

    public void eliminar(String id) {
        planAlimentacionRepo.deleteById(id);
    }

    // ASIGNAR A SOCIO
    public String asignarASocio(String email, String planId) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        PlanAlimentacion plan = planAlimentacionRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("Plan de alimentación no encontrado"));

        usuario.setPlanAlimentacionId(plan.getId()); // Recuerda agregar este campo al modelo Usuario
        usuarioRepo.save(usuario);
        
        return "Plan de alimentación '" + plan.getNombrePlan() + "' asignado a " + usuario.getNombreCompleto();
    }

    public PlanAlimentacion obtenerPlanDeUsuario(String email) {
        Usuario usuario = usuarioRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (usuario.getPlanAlimentacionId() == null) {
            throw new RuntimeException("El socio no tiene un plan de alimentación asignado");
        }

        return planAlimentacionRepo.findById(usuario.getPlanAlimentacionId())
                .orElseThrow(() -> new RuntimeException("El plan de alimentación ya no existe"));
    }
}