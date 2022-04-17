package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Verificacion;

import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/verificacion")
@CrossOrigin(origins = {"*"})
public class VerificacionController {
    @Autowired
    public VerificacionService service;

    // 4.4 Validar código de verificación
    @PostMapping("/")
    public ResponseEntity<Mensaje> verificarUsuario(@RequestBody VerificacionDTO verificacionDTO) {
        //try {
        return service.verificarUsuario(verificacionDTO.getCorreo(), verificacionDTO.getCodigo());
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}

    }
}
