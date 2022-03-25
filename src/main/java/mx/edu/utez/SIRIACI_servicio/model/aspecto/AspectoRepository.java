package mx.edu.utez.SIRIACI_servicio.model.aspecto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AspectoRepository extends JpaRepository<Aspecto, Byte> {
    Optional<Aspecto> findById(Byte id);
}
