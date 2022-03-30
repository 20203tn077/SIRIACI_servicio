package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responsable/incidencias")
@CrossOrigin(origins = {"*"})
public class IndicenciaControllerResponsable {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;
    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    IncidenciaService service;

    // 2.3 Consultar reporte de incidencia
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerIncidencia(@PathVariable long id) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }
        //try {
        return service.obtenerIncidenciaResponsable(usuario.getId(), id);
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 2.5 Consultar reportes de incidencia
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
            return service.obtenerIncidenciasResponsable(usuario.getId(), PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()));
        } else {
            return service.obtenerIncidenciasResponsable(usuario.getId(), PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()), filtro);
        }
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 2.6 Atender reporte de incidencia
    @PatchMapping("/{id}")
    public ResponseEntity<Mensaje> atenderIncidencia(@RequestBody IncidenciaAtenderDTO incidenciaAtenderDTO, @PathVariable long id) {
        DetalleUsuario usuario = null;
        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }
        //try {
        return service.atenderIncidenciaResponsable(new Incidencia(
                id,
                incidenciaAtenderDTO.getComentario(),
                new Usuario(usuario.getId())
        ));
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
