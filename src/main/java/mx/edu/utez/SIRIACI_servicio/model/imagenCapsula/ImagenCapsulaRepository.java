package mx.edu.utez.SIRIACI_servicio.model.imagenCapsula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenCapsulaRepository extends JpaRepository<ImagenCapsula, Long> {
}
