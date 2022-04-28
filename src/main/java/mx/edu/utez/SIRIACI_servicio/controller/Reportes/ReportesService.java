package mx.edu.utez.SIRIACI_servicio.controller.Reportes;

import mx.edu.utez.SIRIACI_servicio.controller.Incidencias.IncidenciaSalidaDTO;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.IncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReportesService {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerReporteAdministrador(LocalDate fechaInicio, LocalDate fechaFin, List<Byte> aspectos) {
        if (fechaInicio.isAfter(fechaFin)) return new ResponseEntity<>(new Mensaje(true, "Fecha de inicio posterior a fecha final.", null, null), HttpStatus.BAD_REQUEST);
        if (fechaInicio.isAfter(LocalDate.now()) || fechaFin.isAfter(LocalDate.now())) return new ResponseEntity<>(new Mensaje(true, "Rango posterior a fecha actual.", null, null), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByActivoIsTrueAndTiempoIncidenciaBetweenAndAspecto_IdIn(fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59), aspectos).stream().map(incidencia -> new IncidenciaSalidaDTO(incidencia))), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerReporteResponsable(LocalDate fechaInicio, LocalDate fechaFin, Long idUsuario) {
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(idUsuario);
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente.", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();
        if (fechaInicio.isAfter(fechaFin)) return new ResponseEntity<>(new Mensaje(true, "Fecha de inicio posterior a fecha final.", null, null), HttpStatus.BAD_REQUEST);
        if (fechaInicio.isAfter(LocalDate.now()) || fechaFin.isAfter(LocalDate.now())) return new ResponseEntity<>(new Mensaje(true, "Rango posterior a fecha actual.", null, null), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByActivoIsTrueAndTiempoIncidenciaBetweenAndAspecto_Id(fechaInicio.atStartOfDay(), fechaFin.atTime(23, 59), usuario.getResponsable().getAspecto().getId()).stream().map(incidencia -> new IncidenciaSalidaDTO(incidencia))), HttpStatus.OK);
    }
}
