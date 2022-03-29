package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/administrador/usuarios")
@CrossOrigin(origins = {"*"})
public class UsuarioControllerAdministrador {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;

    private final static Logger logger = LoggerFactory.getLogger(UsuarioControllerAdministrador.class);
    @Autowired
    UsuarioService service;

    // 1.1 Registrar nuevo usuario
    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarUsuario(@RequestBody UsuarioAdministradorDTO usuarioAdministradorDTO) {
        //try {
            return service.registrarUsuario(
                    new Usuario(
                            usuarioAdministradorDTO.getNombre(),
                            usuarioAdministradorDTO.getApellido1(),
                            usuarioAdministradorDTO.getApellido2(),
                            usuarioAdministradorDTO.getCorreo(),
                            usuarioAdministradorDTO.getTelefono(),
                            usuarioAdministradorDTO.getContrasena()
                    ),
                    new Administrador(),
                    new Responsable(
                            new Aspecto(usuarioAdministradorDTO.getAspecto())
                    ),
                    new Estudiante(
                            usuarioAdministradorDTO.getCuatrimestre(),
                            usuarioAdministradorDTO.getGrupo(),
                            new Carrera(usuarioAdministradorDTO.getCarrera())
                    ),
                    usuarioAdministradorDTO.isAdministrador(),
                    usuarioAdministradorDTO.isResponsable()
            );
        /*} catch (Exception e) {
            logger.error("Error en método registrarUsuario" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error al registrar al usuario", null, null), HttpStatus.BAD_REQUEST);
        }*/
    }

    // 1.2 Consultar usuarios
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

    // 1.3 Consultar usuario
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerUsuario(@PathVariable long id) {
        //try {
        return service.obtenerUsuario(id);
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 1.4 Modificar usuario
    @PatchMapping("/{id}")
    public ResponseEntity<Mensaje> modificarUsuario(@RequestBody UsuarioAdministradorDTO usuarioAdministradorDTO, @PathVariable long id) {
        //try {
        return service.modificarUsuario(
                new Usuario(
                        id,
                        usuarioAdministradorDTO.getNombre(),
                        usuarioAdministradorDTO.getApellido1(),
                        usuarioAdministradorDTO.getApellido2(),
                        usuarioAdministradorDTO.getCorreo(),
                        usuarioAdministradorDTO.getTelefono(),
                        usuarioAdministradorDTO.getContrasena()
                ),
                new Administrador(),
                new Responsable(
                        new Aspecto(usuarioAdministradorDTO.getAspecto())
                ),
                new Estudiante(
                        usuarioAdministradorDTO.getCuatrimestre(),
                        usuarioAdministradorDTO.getGrupo(),
                        new Carrera(usuarioAdministradorDTO.getCarrera())
                ),
                usuarioAdministradorDTO.isAdministrador(),
                usuarioAdministradorDTO.isResponsable()
        );
        /*} catch (Exception e) {
            logger.error("Error en método registrarUsuario" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error al modificar al usuario", null, null), HttpStatus.BAD_REQUEST);
        }*/
    }

    // 1.5 Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminarUsuario(@PathVariable long id) {
        //try {
        return service.eliminarUsuario(id);
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }
}
