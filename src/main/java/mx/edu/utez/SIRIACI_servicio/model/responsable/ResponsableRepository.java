package mx.edu.utez.SIRIACI_servicio.model.responsable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
    boolean existsById(long id);
    Responsable findById(long id);
    Optional<Responsable> findByAspectoId(byte id);

}
