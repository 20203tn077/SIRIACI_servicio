package mx.edu.utez.SIRIACI_servicio.controller.Reportes;

import mx.edu.utez.SIRIACI_servicio.security.DetalleUsuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/responsable/reportes")
@CrossOrigin(origins = {"*"})
public class ReportesControllerResponsable {
    private final static Logger logger = LoggerFactory.getLogger(ReportesControllerResponsable.class);
    @Autowired
    ReportesService service;

    @PostMapping("/")
    public ResponseEntity<Mensaje> obtenerReporte(@RequestBody ReporteDTO reporteDTO) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación.", null, null), HttpStatus.UNAUTHORIZED);
        }

        try {
            return service.obtenerReporteResponsable(reporteDTO.getFechaInicio(), reporteDTO.getFechaFin(), usuario.getId());
        } catch (Exception e) {
            logger.error("Error en método obtenerReporte: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }
}
