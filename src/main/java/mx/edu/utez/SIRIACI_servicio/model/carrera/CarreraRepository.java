package mx.edu.utez.SIRIACI_servicio.model.carrera;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarreraRepository extends JpaRepository<Carrera, Short> {
    Optional<Carrera> findById(Short id);
}
