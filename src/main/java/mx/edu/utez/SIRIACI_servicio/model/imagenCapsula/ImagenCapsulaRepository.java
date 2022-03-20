package mx.edu.utez.SIRIACI_servicio.model.imagenCapsula;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenCapsulaRepository extends JpaRepository<ImagenCapsula, Long> {
    boolean existsByCapsulaId(long id);
    boolean deleteByCapsulaId(long id);
}
