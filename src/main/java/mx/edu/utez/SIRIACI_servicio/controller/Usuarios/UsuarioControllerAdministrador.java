package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.security.jwt.JwtEntryPoint;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/administrador/usuarios")
@CrossOrigin(origins = {"*"})
public class UsuarioControllerAdministrador {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;

    private final static Logger logger = LoggerFactory.getLogger(UsuarioControllerAdministrador.class);

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

    // 1.1 Registrar nuevo usuario
    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO) {
        //try {
            return service.registrarUsuario(
                    new Usuario(
                            usuarioRegistroDTO.getNombre(),
                            usuarioRegistroDTO.getApellido1(),
                            usuarioRegistroDTO.getApellido2(),
                            usuarioRegistroDTO.getCorreo(),
                            usuarioRegistroDTO.getTelefono(),
                            usuarioRegistroDTO.getContrasena()
                    ),
                    new Administrador(),
                    new Responsable(
                            new Aspecto(usuarioRegistroDTO.getAspecto())
                    ),
                    new Estudiante(
                            usuarioRegistroDTO.getCuatrimestre(),
                            usuarioRegistroDTO.getGrupo(),
                            new Carrera(usuarioRegistroDTO.getCarrera())
                    ),
                    usuarioRegistroDTO.isAdministrador(),
                    usuarioRegistroDTO.isResponsable()
            );
        /*} catch (Exception e) {
            logger.error("Error en método registrarUsuario" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error al registrar al usuario", null, null), HttpStatus.BAD_REQUEST);
        }*/
    }

    // 1.2 Consultar usuarios
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerUsuario(@PathVariable long id) {
        //try {
        return service.obtenerUsuario(id);
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 1.3 Consultar usuario
    @GetMapping("/")
    public ResponseEntity<Mensaje> obtenerUsuario(@RequestParam(required = false) Integer pagina, @RequestParam(required = false) String filtro) {
        //try {
        if (filtro == null) {
            return service.obtenerUsuarios(PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()));
        } else {
            return service.obtenerUsuarios(PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()), filtro);
        }
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 1.4 Modificar usuario
    @PatchMapping("/{id}")
    public ResponseEntity<Mensaje> modificarUsuario(@RequestBody UsuarioRegistroDTO usuarioRegistroDTO, @PathVariable long id) {
        //try {
        return service.modificarUsuario(
                new Usuario(
                        id,
                        usuarioRegistroDTO.getNombre(),
                        usuarioRegistroDTO.getApellido1(),
                        usuarioRegistroDTO.getApellido2(),
                        usuarioRegistroDTO.getCorreo(),
                        usuarioRegistroDTO.getTelefono(),
                        usuarioRegistroDTO.getContrasena()
                ),
                new Administrador(),
                new Responsable(
                        new Aspecto(usuarioRegistroDTO.getAspecto())
                ),
                new Estudiante(
                        usuarioRegistroDTO.getCuatrimestre(),
                        usuarioRegistroDTO.getGrupo(),
                        new Carrera(usuarioRegistroDTO.getCarrera())
                ),
                usuarioRegistroDTO.isAdministrador(),
                usuarioRegistroDTO.isResponsable()
        );
        /*} catch (Exception e) {
            logger.error("Error en método registrarUsuario" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error al modificar al usuario", null, null), HttpStatus.BAD_REQUEST);
        }*/
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminarUsuario(@PathVariable long id) {
        //try {
        return service.eliminarUsuario(id);
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    /*
    public ResponseEntity<Mensaje> () {
        //try {
            return service.;
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }
    */
}
