package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.estado.EstadoRepository;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.ImportanciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.IncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import mx.edu.utez.SIRIACI_servicio.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class IncidenciaService {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    AspectoRepository aspectoRepository;
    @Autowired
    ImportanciaRepository importanciaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EstadoRepository estadoRepository;
    @Autowired
    ImagenIncidenciaRepository imagenIncidenciaRepository;


    // 2.1 Registrar nuevo reporte de incidencia
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarIncidencia(Incidencia incidencia, List<ImagenIncidencia> imagenes) {
        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        error = Validador.validarDescripcionIncidencia(incidencia.getDescripcion());
        if (error.isPresent()) errores.put("descripcion", error.get());

        error = Validador.validarUbicacionIncidencia(incidencia.getLatitud(), incidencia.getLongitud());
        if (error.isPresent()) errores.put("ubicacion", error.get());

        Optional<Aspecto> aspecto = aspectoRepository.findById(incidencia.getAspecto().getId());
        if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionada no existe");
        else incidencia.setAspecto(aspecto.get());

        Optional<Importancia> importancia = importanciaRepository.findById(incidencia.getImportancia().getId());
        if (importancia.isEmpty()) errores.put("importancia", "El nivel de importancia seleccionada no existe");
        else incidencia.setImportancia(importancia.get());

        Optional<Usuario> usuario = usuarioRepository.findByIdAndActivoIsTrue(incidencia.getUsuario().getId());
        if (usuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        else incidencia.setUsuario(usuario.get());

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar la incidencia", errores, null), HttpStatus.BAD_REQUEST);

        incidencia.setEstado(estadoRepository.findFirstByOrderById());

        incidencia = incidenciaRepository.save(incidencia);

        for (ImagenIncidencia imagen : imagenes) {
            imagen.setIncidencia(incidencia);
            imagenIncidenciaRepository.save(imagen);
        }

        return new ResponseEntity<>(new Mensaje(false, "Incidencia registrada", null, incidencia), HttpStatus.OK);

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
//        Map<String, String> errores = new HashMap<>();
//        Optional<String> error;
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
//        Map<String, String> errores = new HashMap<>();
//        Optional<String> error;
//    }
//
//    // 2.7 Eliminar reporte de incidencia
//    @Transactional(rollbackFor = {SQLException.class})
//    public ResponseEntity<Mensaje> entrada() {
//
//    }
}
