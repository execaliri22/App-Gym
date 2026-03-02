package com.example.App_Gym.repositories;

import com.example.App_Gym.models.PlanEntrenamiento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanEntrenamientoRepository extends MongoRepository<PlanEntrenamiento, String> {
    // Al heredar de MongoRepository, ya tienes:
    // save(), findById(), findAll(), deleteById(), etc.
}