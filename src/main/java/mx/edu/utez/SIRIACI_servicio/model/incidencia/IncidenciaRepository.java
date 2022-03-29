package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {
    Page<Incidencia> findAllByActivoIsTrueAndUsuario_Id(long id, Pageable pageable);
    Page<Incidencia> findAllByActivoIsTrueAndDescripcionContainsAndUsuario_Id(String filtro, long id, Pageable pageable);
    Optional<Incidencia> findByIdAndActivoIsTrue(Long id);
}
