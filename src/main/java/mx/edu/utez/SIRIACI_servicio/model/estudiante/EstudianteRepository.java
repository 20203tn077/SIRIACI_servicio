package mx.edu.utez.SIRIACI_servicio.model.estudiante;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    boolean existsById(long id);
    Estudiante findById(long id);
}
