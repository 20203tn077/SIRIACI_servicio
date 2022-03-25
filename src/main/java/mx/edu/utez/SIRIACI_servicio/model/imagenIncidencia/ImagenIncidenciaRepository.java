package mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImagenIncidenciaRepository extends JpaRepository<ImagenIncidencia, Long> {
}
