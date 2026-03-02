package com.example.App_Gym.repositories;

import com.example.App_Gym.models.Pago;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoRepository extends MongoRepository<Pago, String> {
    // Busca los pagos de un usuario específico ordenados por vencimiento
    List<Pago> findByUsuarioIdOrderByFechaVencimientoDesc(String usuarioId);
}