package mx.edu.utez.SIRIACI_servicio.controller.NotificacionesYMensajes;

import mx.edu.utez.SIRIACI_servicio.controller.ImagenDTO;
import mx.edu.utez.SIRIACI_servicio.controller.Incidencias.IncidenciaController;
import mx.edu.utez.SIRIACI_servicio.controller.Incidencias.IncidenciaDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = {"*"})
public class NotificacionesController {
    private final static Logger logger = LoggerFactory.getLogger(NotificacionesController.class);
    @Autowired
    NotificacionesService service;

    @DeleteMapping("/")
    public ResponseEntity<Mensaje> desuscribirse(@RequestBody NotificacionesDTO notificacionesDTO) {
        try {
            service.desuscribirse(notificacionesDTO.getToken());
            return new ResponseEntity<>(new Mensaje(false, null, null, null), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error en método desuscribirse: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.OK);
        }
    }
}
