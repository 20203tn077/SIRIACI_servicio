package mx.edu.utez.SIRIACI_servicio.model.dispositivoMovil;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispositivoMovilRepository extends JpaRepository<DispositivoMovil, Long> {
    Optional<DispositivoMovil> findByToken(String token);
}
