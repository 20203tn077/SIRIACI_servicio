package mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolicitudRestablecimientoRepository extends JpaRepository<SolicitudRestablecimiento, Long> {
    Optional<SolicitudRestablecimiento> findByUsuario_Id(long id);
}
