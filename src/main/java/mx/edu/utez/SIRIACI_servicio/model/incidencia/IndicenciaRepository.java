package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface IndicenciaRepository extends JpaRepository<Incidencia, Long> {
    Optional<Incidencia> findIncidenciaByTiempoIncidenciaBetween(Date value1, Date value2);
    boolean existsIncidenciaByTiempoIncidenciaBetween(Date value1, Date value2);

}
