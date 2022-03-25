package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface IndicenciaRepository extends JpaRepository<Incidencia, Long> {
}
