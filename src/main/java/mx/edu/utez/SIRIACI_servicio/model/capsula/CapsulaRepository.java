package mx.edu.utez.SIRIACI_servicio.model.capsula;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapsulaRepository extends JpaRepository<Capsula, Long> {

    Page<Capsula> findAllByActivoIsTrue(Pageable pageable);
    Page<Capsula> findAllByActivoIsTrueAndTituloContains(String filtro, Pageable pageable);
    Page<Capsula> findAllByTituloContains(String filtro, Pageable pageable);
    Page<Capsula> findAllByActivoIsTrueAndUsuario_Id(long id, Pageable pageable);
    Page<Capsula> findAllByActivoIsTrueAndUsuario_IdAndTituloContains(long id, String filtro, Pageable pageable);
}
