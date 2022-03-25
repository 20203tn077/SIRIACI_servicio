package mx.edu.utez.SIRIACI_servicio.controller.Otros;

import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seleccionables/")
@CrossOrigin(origins = {"*"})
public class SeleccionablesController {
    @Autowired
    SeleccionablesService service;

    @GetMapping("importancias/")
    public ResponseEntity<Mensaje> obtenerImportancias() {
        return service.obtenerImportancias();
    }
    @GetMapping("estados/")
    public ResponseEntity<Mensaje> obtenerEstados() {
        return service.obtenerEstados();
    }
    @GetMapping("aspectos/")
    public ResponseEntity<Mensaje> obtenerAspectos() {
        return service.obtenerAspectos();
    }
    @GetMapping("divisiones/")
    public ResponseEntity<Mensaje> obtenerDivisiones() {
        return service.obtenerDivisiones();
    }
    @GetMapping("carreras/")
    public ResponseEntity<Mensaje> obtenerCarreras() {
        return service.obtenerCarreras();
    }
}
