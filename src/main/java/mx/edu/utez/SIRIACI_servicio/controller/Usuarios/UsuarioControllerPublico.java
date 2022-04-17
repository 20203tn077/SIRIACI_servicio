package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publico/usuarios")
@CrossOrigin(origins = {"*"})
public class UsuarioControllerPublico {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioControllerPublico.class);
    @Autowired
    UsuarioService service;

    // 1.6 Registrarse en el sistema
    @PostMapping("/")
    public ResponseEntity<Mensaje> autorregistro(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            return service.autorregistro(
                    new Usuario(
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
        } catch (Exception e) {
            logger.error("Error en método autorregistro: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }
}
