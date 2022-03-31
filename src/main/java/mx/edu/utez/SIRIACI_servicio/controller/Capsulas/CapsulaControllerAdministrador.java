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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/administrador/capsulas")
@CrossOrigin(origins = {"*"})
public class CapsulaControllerAdministrador {
    @Value("${conf.registros_por_pagina}")
    int registrosPorPagina;
    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    CapsulaService service;

    // 3.3 Registrar cápsula informativa
//    public ResponseEntity<Mensaje> registrarCapsula() {
//        //try {
//            return service.;
//        //} catch (Exception e) {
//        //    logger.error("Error en método " + e.getMessage());
//        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
//        //}
//    }

    // 3.4 Consultar cápsulas informativas realizadas
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadas(@RequestParam(required = false) String filtro, @RequestParam(required = false) Integer pagina) {
        //try {
        if (filtro == null) {
            return service.obtenerCapsulasRealizadasAdministrador(PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()));
        } else {
            return service.obtenerCapsulasRealizadasAdministrador(PageRequest.of(pagina != null ? pagina -1 : 0, registrosPorPagina, Sort.by("id").descending()), filtro);
        }
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 3.5 Modificar cápsula informativa
//    public ResponseEntity<Mensaje> modificarCapsula() {
//        //try {
//            return service.;
//        //} catch (Exception e) {
//        //    logger.error("Error en método " + e.getMessage());
//        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
//        //}
//    }

    // 3.6 Eliminar cápsula informativa
//    public ResponseEntity<Mensaje> eliminarCapsula() {
//        //try {
//            return service.;
//        //} catch (Exception e) {
//        //    logger.error("Error en método " + e.getMessage());
//        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
//        //}
//    }
}
