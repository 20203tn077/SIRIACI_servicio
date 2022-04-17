package mx.edu.utez.SIRIACI_servicio.controller.Otros;

import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seleccionables/")
@CrossOrigin(origins = {"*"})
public class SeleccionablesController {
    private final static Logger logger = LoggerFactory.getLogger(SeleccionablesController.class);

    @Autowired
    SeleccionablesService service;

    @GetMapping("importancias/")
    public ResponseEntity<Mensaje> obtenerImportancias() {
        try {
            return service.obtenerImportancias();
        } catch (Exception e) {
            logger.error("Error en método obtenerImportancias: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("estados/")
    public ResponseEntity<Mensaje> obtenerEstados() {
        try {
            return service.obtenerEstados();
        } catch (Exception e) {
            logger.error("Error en método obtenerEstados: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("aspectos/")
    public ResponseEntity<Mensaje> obtenerAspectos() {
        try {
            return service.obtenerAspectos();
        } catch (Exception e) {
            logger.error("Error en método obtenerAspectos: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("carreras/")
    public ResponseEntity<Mensaje> obtenerDivisiones() {
        try {
            return service.obtenerDivisiones();
        } catch (Exception e) {
            logger.error("Error en método obtenerDivisiones: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }

}
