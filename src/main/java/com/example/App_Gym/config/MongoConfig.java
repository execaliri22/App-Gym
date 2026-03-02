package com.example.App_Gym.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = "com.gym.app.repositories")
public class MongoConfig {
    // Aquí puedes añadir convertidores de fechas si fuera necesario en el futuro
}