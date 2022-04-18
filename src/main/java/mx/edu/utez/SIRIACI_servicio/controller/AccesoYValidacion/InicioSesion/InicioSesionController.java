package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.InicioSesion;

import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/iniciosesion")
@CrossOrigin(origins = {"*"})
public class InicioSesionController {
    private final static Logger logger = LoggerFactory.getLogger(InicioSesionController.class);
    @Autowired
    InicioSesionService service;

    // 4.1 Iniciar sesión
    @PostMapping("/")
    public ResponseEntity<Mensaje> iniciarSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDTO) {
        try {
            return service.iniciarSesion(inicioSesionDTO.getCorreo(), inicioSesionDTO.getContrasena(), inicioSesionDTO.getDispositivoMovil());
        } catch (Exception e) {
            logger.error("Error en método  iniciarSesion: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }
}
