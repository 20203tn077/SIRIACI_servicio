package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@Transactional
public class IncidenciaService {
    // 2.1 Registrar nuevo reporte de incidencia
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarIncidencia(Incidencia incidencia, List<ImagenIncidencia> imagenIncidencias) {

    }

//    // 2.2 Consultar reportes de incidencia realizados
//    @Transactional(readOnly = true)
//    public ResponseEntity<Mensaje> salida() {
//
//    }
//
//    // 2.3 Consultar reporte de incidencia
//    @Transactional(readOnly = true)
//    public ResponseEntity<Mensaje> salida() {
//
//    }
//
//    // 2.4 Modificar reporte de incidencia
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> entrada() {
//
//    }
//
//    // 2.5 Consultar reportes de incidencia
//    @Transactional(readOnly = true)
//    public ResponseEntity<Mensaje> salida() {
//
//    }
//
//    // 2.6 Atender reporte de incidencia
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> entrada() {
//
//    }
//
//    // 2.7 Eliminar reporte de incidencia
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> entrada() {
//
//    }
}
