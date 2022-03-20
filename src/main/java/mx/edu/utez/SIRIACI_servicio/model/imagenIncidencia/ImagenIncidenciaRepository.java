package mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenIncidenciaRepository extends JpaRepository<ImagenIncidencia, Long> {
    boolean existsByIncidenciaId(long id);
    boolean deleteByIncidenciaId(long id);
}
