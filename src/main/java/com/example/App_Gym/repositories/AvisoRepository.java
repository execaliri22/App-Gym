package com.example.App_Gym.repositories;

import com.example.App_Gym.models.Aviso;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository 
public interface AvisoRepository extends MongoRepository<Aviso, String> {
    // Buscar solo los avisos que estén activos, ordenados por fecha
    List<Aviso> findByActivoTrueOrderByFechaCreacionDesc();
}