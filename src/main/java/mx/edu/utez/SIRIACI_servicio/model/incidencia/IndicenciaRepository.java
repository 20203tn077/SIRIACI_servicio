package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface IndicenciaRepository extends JpaRepository<Incidencia, Long> {
    Optional<Incidencia> findByTiempoIncidenciaBetween(Date value1, Date value2);
    Optional<Incidencia> findByAspectoIdAndTiempoIncidenciaBetween(byte id,Date value1, Date value2);
    boolean existsByAspectoIdAndTiempoIncidenciaBetween(byte id,Date value1, Date value2);
    boolean existsByTiempoIncidenciaBetween(Date value1, Date value2);
    Optional<Incidencia> findByAspectoId(long id);
    boolean existsById(long id);
    boolean deleteIncidenciaById(long id);
}
