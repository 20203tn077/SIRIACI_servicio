package mx.edu.utez.SIRIACI_servicio.model.bloqueo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BloqueoRepository extends JpaRepository<Bloqueo, Long> {
    /* Consulta especifica para verificar el tiempo de bloqueo aun sin resolver
    String query =  "SELECT CASE WHEN count(id) > 0 THEN true ELSE false END FROM Bloqueo as b where b.usuario_id = :id";
    @Query(value = query)
    boolean existsUsuarioByTime(@Param("id") long id);
    */
    boolean deleteById(long id);
    Bloqueo findByUsuarioId(long id);
}
