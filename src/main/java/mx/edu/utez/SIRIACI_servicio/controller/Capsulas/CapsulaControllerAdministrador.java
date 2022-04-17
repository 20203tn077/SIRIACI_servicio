package mx.edu.utez.SIRIACI_servicio.controller.Capsulas;

import mx.edu.utez.SIRIACI_servicio.controller.ImagenDTO;
import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
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
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/administrador/capsulas")
@CrossOrigin(origins = {"*"})
public class CapsulaControllerAdministrador {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;
    private final static Logger logger = LoggerFactory.getLogger(CapsulaControllerAdministrador.class);
    @Autowired
    CapsulaService service;

    // 3.2 Consultar cápsula informativa
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerCapsula(@PathVariable long id) {
        try {
        return service.obtenerCapsulaAdministrador(id);
        } catch (Exception e) {
            logger.error("Error en método obtenerCapsula: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    // 3.3 Registrar cápsula informativa
    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarCapsula(@RequestBody CapsulaDTO capsulaDTO) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.UNAUTHORIZED);
        }

        try {
        List<ImagenCapsula> imagenes = new ArrayList<>();
        if (capsulaDTO.getImagenesCapsula() != null) {
            for (ImagenDTO imagen : capsulaDTO.getImagenesCapsula()) {
                if (imagen.getImagen() != null) imagenes.add(new ImagenCapsula(Base64.getDecoder().decode(imagen.getImagen())));
            }
        }
        return service.registrarCapsula(
                new Capsula(
                        capsulaDTO.getTitulo(),
                        capsulaDTO.contenido,
                        LocalDateTime.now(),
                        new Usuario(usuario.getId())
                ),
                imagenes
        );
        } catch (Exception e) {
            logger.error("Error en método registrarCapsula: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    // 3.4 Consultar cápsulas informativas realizadas
    @GetMapping("/")
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadas(@RequestParam(required = false) String filtro, @RequestParam(required = false) Integer pagina) {
        try {
        if (filtro == null) {
            return service.obtenerCapsulasRealizadasAdministrador(PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()));
        } else {
            return service.obtenerCapsulasRealizadasAdministrador(PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()), filtro);
        }
        } catch (Exception e) {
            logger.error("Error en método obtenerCapsulasRealizadas: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    // 3.5 Modificar cápsula informativa
    @PatchMapping("/{id}")
    public ResponseEntity<Mensaje> modificarCapsula(@RequestBody CapsulaDTO capsulaDTO, @PathVariable long id) {

        List<ImagenCapsula> imagenesRegistrar = new ArrayList<>();
        List<ImagenCapsula> imagenesEliminar = new ArrayList<>();
        if (capsulaDTO.getImagenesCapsula() != null) {
            for (ImagenDTO imagen : capsulaDTO.getImagenesCapsula()) {
                if (imagen.getImagen() != null) imagenesRegistrar.add(new ImagenCapsula(Base64.getDecoder().decode(imagen.getImagen())));
                else if (imagen.getId() != null) imagenesEliminar.add(new ImagenCapsula(imagen.getId()));
            }
        }

        try {
        return service.modificarCapsulaAdministrador(
                new Capsula(
                        id,
                        capsulaDTO.getTitulo(),
                        capsulaDTO.contenido
                ),
                imagenesRegistrar,
                imagenesEliminar
        );
        } catch (Exception e) {
            logger.error("Error en método modificarCapsula: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    // 3.6 Eliminar cápsula informativa
    @DeleteMapping("/{id}")
    public ResponseEntity<Mensaje> eliminarCapsula(@PathVariable long id) {
        try {
        return service.eliminarCapsulaAdministrador(id);
        } catch (Exception e) {
            logger.error("Error en método eliminarCapsula: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }
}
