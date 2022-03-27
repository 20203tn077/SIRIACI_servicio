package mx.edu.utez.SIRIACI_servicio.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreoAndActivoIsTrue(String correo);
    Optional<Usuario> findByCorreoAndActivoIsTrue(String correo);
    Optional<Usuario> findByIdAndActivoIsTrue(long id);
}
