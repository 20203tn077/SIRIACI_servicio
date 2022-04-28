package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    Page<Incidencia> findAllByActivoIsTrueAndUsuario_Id(long id, Pageable pageable);
    Page<Incidencia> findAllByActivoIsTrueAndDescripcionContainsAndUsuario_Id(String filtro, long id, Pageable pageable);
    Page<Incidencia> findAllByDescripcionContains(String filtro, Pageable pageable);
    Page<Incidencia> findAllByActivoIsTrueAndAspecto_Id(byte id, Pageable pageable);
    Page<Incidencia> findAllByActivoIsTrueAndDescripcionContainsAndAspecto_Id(String filtro, byte id, Pageable pageable);
    List<Incidencia> findAllByActivoIsTrueAndTiempoIncidenciaBetweenAndAspecto_Id(LocalDateTime fechaInicio, LocalDateTime fechaFin, Byte id);
    List<Incidencia> findAllByActivoIsTrueAndTiempoIncidenciaBetweenAndAspecto_IdIn(LocalDateTime fechaInicio, LocalDateTime fechaFin, List<Byte> ids);
    Optional<Incidencia> findByIdAndActivoIsTrue(Long id);
    Optional<Incidencia> findByCodigoAndActivoIsTrue(UUID codigo);
    Optional<Incidencia> findByCodigo(UUID codigo);
}
