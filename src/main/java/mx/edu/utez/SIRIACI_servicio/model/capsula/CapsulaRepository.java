package mx.edu.utez.SIRIACI_servicio.model.capsula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapsulaRepository extends JpaRepository<Capsula, Long> {
}
