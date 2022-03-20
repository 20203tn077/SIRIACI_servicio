package mx.edu.utez.SIRIACI_servicio.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreoAndActivoIsTrue(String correo);
    Usuario findByCorreoAndContrasenaAndActivoIsTrue(String correo, String contrasena);
    boolean existsByCorreoAndContrasenaAndActivoIsTrue(String correo, String contrasena);

}
