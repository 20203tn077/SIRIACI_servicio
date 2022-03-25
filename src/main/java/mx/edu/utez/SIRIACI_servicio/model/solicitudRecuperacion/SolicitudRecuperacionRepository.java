package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SolicitudRecuperacionRepository extends JpaRepository<SolicitudRecuperacion, Long> {
}
