package mx.edu.utez.SIRIACI_servicio.controller.Reportes;

import mx.edu.utez.SIRIACI_servicio.controller.Incidencias.IncidenciaControllerAdministrador;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/administrador/reportes")
@CrossOrigin(origins = {"*"})
public class ReportesControllerAdministrador {
    private final static Logger logger = LoggerFactory.getLogger(ReportesControllerAdministrador.class);
    @Autowired
    ReportesService service;

    @PostMapping("/")
    public ResponseEntity<Mensaje> obtenerReporte(@RequestBody ReporteDTO reporteDTO) {
        try {
            return service.obtenerReporteAdministrador(reporteDTO.getFechaInicio(), reporteDTO.getFechaFin(), reporteDTO.getAspectos());
        } catch (Exception e) {
            logger.error("Error en método obtenerReporte: " + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error en el servidor.", null, null), HttpStatus.BAD_REQUEST);
        }
    }
}