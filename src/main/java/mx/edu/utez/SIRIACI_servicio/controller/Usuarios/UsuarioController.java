package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.security.DetalleUsuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"*"})
public class UsuarioController {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    UsuarioService service;

    @PatchMapping("/")
    // 1.7 Modificar datos personales
    public ResponseEntity<Mensaje> automodificacion(@RequestBody UsuarioDTO usuarioDTO) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.BAD_REQUEST);
        }

        //try {
        return service.automodificacion(
                new Usuario(
                        usuario.getId(),
                        usuarioDTO.getNombre(),
                        usuarioDTO.getApellido1(),
                        usuarioDTO.getApellido2(),
                        usuarioDTO.getCorreo(),
                        usuarioDTO.getTelefono(),
                        usuarioDTO.getContrasena()
                ),
                new Estudiante(
                        usuarioDTO.getCuatrimestre(),
                        usuarioDTO.getGrupo(),
                        new Carrera(usuarioDTO.getCarrera())
                )
        );
        /*} catch (Exception e) {
            logger.error("Error en método registrarUsuario" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error al registrar al usuario", null, null), HttpStatus.BAD_REQUEST);
        }*/
    }

    @GetMapping("/perfil/")
    // 1.8 Consultar perfíl
    public ResponseEntity<Mensaje> perfil() {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.BAD_REQUEST);
        }

        //try {
        return service.obtenerUsuario(usuario.getId());
        /*} catch (Exception e) {
            logger.error("Error en método registrarUsuario" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error al registrar al usuario", null, null), HttpStatus.BAD_REQUEST);
        }*/
    }
}
