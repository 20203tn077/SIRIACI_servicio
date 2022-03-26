package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = {"*"})
public class UsuarioController {
    @Autowired
    UsuarioService service;

    /*
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String telefono;
    private String contrasena;
    private boolean activo;
    private boolean comunidadUtez;
    private Administrador admnistrador;
    private Estudiante estudiante;
    private Bloqueo bloqueo;
    private NoVerificado noVerificado;
    private Responsable responsable;
    private List<Incidencia> incidencias;
    private List<Notificacion> notificaciones;
    private SolicitudRecuperacion solicitudRecuperación;
    private List<DispositivoMovil> dispositivosMoviles;
    private List<Capsula> capsulas;*/

    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        return service.registrarUsuario(
                new Usuario(
                        usuarioRegistroDTO.getNombre(),
                        usuarioRegistroDTO.getApellido1(),
                        usuarioRegistroDTO.getApellido2(),
                        usuarioRegistroDTO.getCorreo(),
                        usuarioRegistroDTO.getTelefono(),
                        usuarioRegistroDTO.getContrasena()
                ),
                usuarioRegistroDTO.isAdmnistrador() ? new Administrador() : null,
                usuarioRegistroDTO.isResponsable() ? new Responsable(
                        new Aspecto(usuarioRegistroDTO.getAspecto())
                ) : null,
                new Estudiante(
                        usuarioRegistroDTO.getCuatrimestre(),
                        usuarioRegistroDTO.getGrupo(),
                        new Carrera(usuarioRegistroDTO.getCarrera())
                )
        );
    }

    @PatchMapping("/asd/")
    public ResponseEntity<Mensaje> modificarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        System.out.println(usuarioRegistroDTO.getCuatrimestre());
        return new ResponseEntity<Mensaje>(new Mensaje(false, "ok", null, null), HttpStatus.OK);
    }
}
