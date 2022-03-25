package mx.edu.utez.SIRIACI_servicio.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreoAndActivoIsTrue(String correo);
}
