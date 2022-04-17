package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Restablecimiento;

import com.devskiller.friendly_id.FriendlyId;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento.SolicitudRestablecimiento;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/restablecimiento")
@CrossOrigin(origins = {"*"})
public class RestablecimientoController {
    @Autowired
    RestablecimientoService service;

    // 4.5 Solicitar restablecimiento de contraseña
    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarSolicitud(@RequestBody RestablecimientoDTO restablecimientoDTO) {
        //try {
        return service.registrarSolicitud(new SolicitudRestablecimiento(
                FriendlyId.createFriendlyId().substring(0,6).toUpperCase(),
                LocalDateTime.now(),
                new Usuario(restablecimientoDTO.getCorreo())
        ));
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }

    // 4.6 Validar código de recuperación
    // 4.7 Establecer nueva contraseña
    @PatchMapping("/")
    public ResponseEntity<Mensaje> validarYRestablecer(@RequestBody RestablecimientoDTO restablecimientoDTO) {
        //try {
        if (restablecimientoDTO.getContrasena() == null) return service.verificarCodigo(new SolicitudRestablecimiento(
                restablecimientoDTO.getCodigo(),
                new Usuario(restablecimientoDTO.getCorreo())
        ));
        else return service.modificarContrasena(new SolicitudRestablecimiento(
                restablecimientoDTO.getCodigo(),
                new Usuario(restablecimientoDTO.getCorreo(), restablecimientoDTO.getContrasena())
        ));
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }
}
