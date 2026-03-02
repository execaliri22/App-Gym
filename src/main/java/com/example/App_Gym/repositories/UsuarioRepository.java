package com.example.App_Gym.repositories;

import com.example.App_Gym.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@SuppressWarnings("unused")
@Repository
public interface UsuarioRepository extends MongoRepository<Usuario, String> {
    
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByDni(String dni);
    
    Boolean existsByEmail(String email);
    Boolean existsByDni(String dni);

   
}