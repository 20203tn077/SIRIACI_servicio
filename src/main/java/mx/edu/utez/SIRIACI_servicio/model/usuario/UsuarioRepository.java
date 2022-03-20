package mx.edu.utez.SIRIACI_servicio.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreoAndActivoIsTrue(String correo);
    boolean existsByTelefonoAndActivoIsTrue(String telefono);
    boolean existsByIdAndComunidadUtezIsTrue(long id);
    Usuario findUsuarioByCorreoAndContrasenaAndActivoIsTrue(String correo, String contrasena);
    boolean existsByCorreoAndContrasenaAndActivoIsTrue(String correo, String contrasena);
    Usuario findUsuarioByCorreoAndActivoIsTrue(String correo);
    boolean deleteUsuarioByCorreo(String correo);
    boolean existsByContrasenaAndActivoIsTrue(String contrasena);

}
