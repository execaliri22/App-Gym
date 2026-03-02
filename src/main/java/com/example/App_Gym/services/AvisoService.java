package com.example.App_Gym.services;

import com.example.App_Gym.models.Aviso;
import com.example.App_Gym.repositories.AvisoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AvisoService {

    @Autowired
    private AvisoRepository avisoRepo;

    public List<Aviso> obtenerTodosActivos() {
        return avisoRepo.findByActivoTrueOrderByFechaCreacionDesc();
    }

    public Aviso crear(Aviso aviso) {
        aviso.setActivo(true); // Regla de negocio: Todo aviso nuevo nace activo
        return avisoRepo.save(aviso);
    }

    public Aviso actualizar(String id, Aviso nuevosDatos) {
        return avisoRepo.findById(id)
                .map(aviso -> {
                    aviso.setTitulo(nuevosDatos.getTitulo());
                    aviso.setDescripcion(nuevosDatos.getDescripcion());
                    aviso.setImagenUrl(nuevosDatos.getImagenUrl());
                    aviso.setActivo(nuevosDatos.isActivo());
                    return avisoRepo.save(aviso);
                })
                .orElseThrow(() -> new RuntimeException("El aviso con ID " + id + " no existe"));
    }

    public void eliminar(String id) {
        if (!avisoRepo.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: ID no encontrado");
        }
        avisoRepo.deleteById(id);
    }
}