package mx.edu.utez.SIRIACI_servicio.model.responsable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
}
