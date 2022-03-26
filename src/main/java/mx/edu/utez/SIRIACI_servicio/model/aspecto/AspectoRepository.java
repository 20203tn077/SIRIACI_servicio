package mx.edu.utez.SIRIACI_servicio.model.aspecto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AspectoRepository extends JpaRepository<Aspecto, Byte> {
    Optional<Aspecto> findById(byte id);
}
