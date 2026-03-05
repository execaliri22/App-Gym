package com.example.App_Gym.repositories;

import com.example.App_Gym.models.Mensaje;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MensajeRepository extends MongoRepository<Mensaje, String> {
    
    // Busca la conversación completa entre dos personas ordenada por fecha
    @Query("{ $or: [ { 'remitenteEmail': ?0, 'destinatarioEmail': ?1 }, { 'remitenteEmail': ?1, 'destinatarioEmail': ?0 } ] }")
    List<Mensaje> findChat(String email1, String email2);

    // Para el Admin: Ver con quién tiene mensajes pendientes
    List<Mensaje> findByDestinatarioEmailAndLeidoFalse(String adminEmail);
}