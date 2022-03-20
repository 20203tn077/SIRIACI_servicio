package mx.edu.utez.SIRIACI_servicio.model.administrador;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    boolean existsById(long id);
    Optional<Administrador> findById(long id);
}
