package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Verificacion;

import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verificacion")
@CrossOrigin(origins = {"*"})
public class VerificacionController {
    private final static Logger logger = LoggerFactory.getLogger(VerificacionController.class);
    @Autowired
    public VerificacionService service;

    // 4.4 Validar código de verificación
    @PostMapping("/")
    public ResponseEntity<Mensaje> verificarUsuario(@RequestBody VerificacionDTO verificacionDTO) {
        try {
            return service.verificarUsuario(verificacionDTO.getCorreo(), verificacionDTO.getCodigo());
        } catch (Exception e) {
            logger.error("Error en método verificarUsuario: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }

    }
}
