package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.controller.ImagenDTO;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = {"*"})
public class IncidenciaController {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;
    private final static Logger logger = LoggerFactory.getLogger(IncidenciaController.class);
    @Autowired
    IncidenciaService service;

    // 2.1 Registrar nuevo reporte de incidencia
    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarIncidencia(@RequestBody IncidenciaDTO incidenciaDTO) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }

        try {
            List<ImagenIncidencia> imagenes = new ArrayList<>();
            if (incidenciaDTO.getImagenesIncidencia() != null) {
                for (ImagenDTO imagen : incidenciaDTO.getImagenesIncidencia()) {
                    if (imagen.getImagen() != null)
                        imagenes.add(new ImagenIncidencia(Base64.getDecoder().decode(imagen.getImagen())));
                }
            }
            return service.registrarIncidencia(new Incidencia(
                            incidenciaDTO.getDescripcion(),
                            LocalDateTime.now(),
                            incidenciaDTO.getLongitud(),
                            incidenciaDTO.getLatitud(),
                            UUID.randomUUID(),
                            new Importancia(incidenciaDTO.getImportancia()),
                            new Aspecto(incidenciaDTO.getAspecto()),
                            new Usuario(usuario.getId())
                    ),
                    imagenes
            );
        } catch (Exception e) {
            logger.error("Error en método registrarIncidencia: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    // 2.2 Consultar reportes de incidencia realizados
    @GetMapping("/")
    public ResponseEntity<Mensaje> obtenerIncidenciasRealizadas(@RequestParam(required = false) Integer pagina, @RequestParam(required = false) String filtro) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }
        try {
            if (filtro == null) {
                return service.obtenerIncidenciasRealizadas(usuario.getId(), PageRequest.of(pagina != null ? pagina - 1 : 0, registrosPorPagina, Sort.by("id").descending()));
            } else {
                return service.obtenerIncidenciasRealizadas(usuario.getId(), PageRequest.of(pagina != null ? pagina - 1 : 0, registrosPorPagina, Sort.by("id").descending()), filtro);
            }
        } catch (Exception e) {
            logger.error("Error en método obtenerIncidenciasRealizadas: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

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
        try {
            return service.obtenerIncidencia(usuario.getId(), id);
        } catch (Exception e) {
            logger.error("Error en método obtenerIncidencia: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    // 2.4 Modificar reporte de incidencia
    @PatchMapping("/{id}")
    public ResponseEntity<Mensaje> modificarIncidencia(@RequestBody IncidenciaDTO incidenciaDTO, @PathVariable long id) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }

        try {
            List<ImagenIncidencia> imagenesRegistrar = new ArrayList<>();
            List<ImagenIncidencia> imagenesEliminar = new ArrayList<>();
            if (incidenciaDTO.getImagenesIncidencia() != null) {
                for (ImagenDTO imagen : incidenciaDTO.getImagenesIncidencia()) {
                    if (imagen.getImagen() != null)
                        imagenesRegistrar.add(new ImagenIncidencia(Base64.getDecoder().decode(imagen.getImagen())));
                    else if (imagen.getId() != null) imagenesEliminar.add(new ImagenIncidencia(imagen.getId()));
                }
            }
            return service.modificarIncidencia(new Incidencia(
                            id,
                            incidenciaDTO.getDescripcion(),
                            incidenciaDTO.getLongitud(),
                            incidenciaDTO.getLatitud(),
                            new Importancia(incidenciaDTO.getImportancia()),
                            new Aspecto(incidenciaDTO.getAspecto()),
                            new Usuario(usuario.getId())
                    ),
                    imagenesRegistrar,
                    imagenesEliminar
            );
        } catch (Exception e) {
            logger.error("Error en método modificarIncidencia: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }
}
