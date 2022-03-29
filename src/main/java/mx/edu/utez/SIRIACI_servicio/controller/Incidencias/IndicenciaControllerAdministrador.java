package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/administrador/incidencias")
@CrossOrigin(origins = {"*"})
public class IndicenciaControllerAdministrador {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;
    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    IncidenciaService service;
    @GetMapping("/")
    public ResponseEntity<Mensaje> obtenerIncidencias(@RequestParam(required = false) Integer pagina, @RequestParam(required = false) String filtro) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }
        //try {
        if (filtro == null) {
            return service.obtenerIncidenciasAdministrador(usuario.getId(), PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()));
        } else {
            return service.obtenerIncidenciasAdministrador(usuario.getId(), PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()), filtro);
        }
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
