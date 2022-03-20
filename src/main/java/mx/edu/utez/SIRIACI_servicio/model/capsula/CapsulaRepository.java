package mx.edu.utez.SIRIACI_servicio.model.capsula;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CapsulaRepository extends JpaRepository<Capsula, Long> {
    // Eliminacion y verificacion de capsula para un Administrador
    boolean existsById(long id);
    boolean deleteById(long id);
    // Eliminacion y verificacion de capsula para un Resposable
    boolean existsByResponsableId(long id);
    boolean deleteByResponsableId(long id);
}
