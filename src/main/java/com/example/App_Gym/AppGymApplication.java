package com.example.App_Gym;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.example.App_Gym.repositories")
@EntityScan(basePackages = "com.example.App_Gym.models")
public class AppGymApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppGymApplication.class, args);
    }
}