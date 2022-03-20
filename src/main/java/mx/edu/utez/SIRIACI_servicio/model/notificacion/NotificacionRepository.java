package mx.edu.utez.SIRIACI_servicio.model.notificacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    boolean existsByUsuarioId(long id);
    boolean existsById(long id);
    Notificacion findByUsuarioId(long id);
}
