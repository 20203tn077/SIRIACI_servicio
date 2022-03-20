package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRecuperacionRepository extends JpaRepository<SolicitudRecuperacion, Long> {
    boolean existsByCodigo(String codigo);
    boolean deleteById(long id);
     /* Consulta especifica para verificar el tiempo de bloqueo aun sin resolver
    String query =  "SELECT CASE WHEN count(id) > 0 THEN true ELSE false END FROM solicitud_recuperacion as b where b.usuario_id = :id AND (select TIMESTAMPDIFF(MINUTE,tiempo_solicitud,now()) from solicitud_recuperacion where usuario_id = :id)<10; ";
    @Query(value = query)
    boolean existsUsuarioByTime(@Param("id") long id);
    */
}
