package mx.edu.utez.SIRIACI_servicio.model.capsula;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CapsulaRepository extends JpaRepository<Capsula, Long> {
    Optional<Capsula> findByIdAndActivoIsTrue(Long id);
    Page<Capsula> findAllByActivoIsTrue(Pageable pageable);
    Page<Capsula> findAllByActivoIsTrueAndTituloContains(String filtro, Pageable pageable);
    Page<Capsula> findAllByTituloContains(String filtro, Pageable pageable);
    Page<Capsula> findAllByUsuario_Id(long id, Pageable pageable);
    Page<Capsula> findAllByUsuario_IdAndTituloContains(long id, String filtro, Pageable pageable);
}
