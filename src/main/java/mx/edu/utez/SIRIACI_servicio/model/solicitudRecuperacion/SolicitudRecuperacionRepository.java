package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolicitudRecuperacionRepository extends JpaRepository<SolicitudRecuperacion, Long> {
    Optional<SolicitudRecuperacion> findByUsuario_Id(long id);
}
