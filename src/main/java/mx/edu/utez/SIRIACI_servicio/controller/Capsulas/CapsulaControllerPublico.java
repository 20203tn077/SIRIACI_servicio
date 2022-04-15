package mx.edu.utez.SIRIACI_servicio.controller.Capsulas;

import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
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
@RequestMapping("/api/publico/capsulas")
@CrossOrigin(origins = {"*"})
public class CapsulaControllerPublico {
    @Value("${conf.capsulas_por_pagina}")
    int capsulasPorPagina;
    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    CapsulaService service;

    // 3.1 Consultar cápsulas informativas
    @GetMapping("/")
    public ResponseEntity<Mensaje> obtenerCapsulas(@RequestParam(required = false) String filtro, @RequestParam(required = false) Integer pagina) {
        //try {
        if (filtro == null) {
            return service.obtenerCapsulas(PageRequest.of(pagina != null ? pagina -1 : 0, capsulasPorPagina, Sort.by("id").descending()));
        } else {
            return service.obtenerCapsulas(PageRequest.of(pagina != null ? pagina -1 : 0, capsulasPorPagina, Sort.by("id").descending()), filtro);
        }
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 3.2 Consultar cápsula informativa
    @GetMapping("/{id}")
    public ResponseEntity<Mensaje> obtenerCapsula(@PathVariable long id) {
        //try {
            return service.obtenerCapsula(id);
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }
}
