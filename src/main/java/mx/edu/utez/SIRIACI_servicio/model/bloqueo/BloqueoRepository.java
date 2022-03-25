package mx.edu.utez.SIRIACI_servicio.model.bloqueo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BloqueoRepository extends JpaRepository<Bloqueo, Long> {
}
