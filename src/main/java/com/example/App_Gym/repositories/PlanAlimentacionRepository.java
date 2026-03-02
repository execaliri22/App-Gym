package com.example.App_Gym.repositories;

import com.example.App_Gym.models.PlanAlimentacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanAlimentacionRepository extends MongoRepository<PlanAlimentacion, String> {
}