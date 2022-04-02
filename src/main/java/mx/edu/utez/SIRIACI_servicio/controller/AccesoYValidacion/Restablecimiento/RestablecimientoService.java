package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Restablecimiento;

import mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion.SolicitudRecuperacion;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion.SolicitudRecuperacionRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import mx.edu.utez.SIRIACI_servicio.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class RestablecimientoService {
    @Value("${conf.duracion_solicitud_restablecimiento}")
    int duracionSolicitud;

    @Autowired
    SolicitudRecuperacionRepository solicitudRecuperacionRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // 4.5 Solicitar restablecimiento de contraseña
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarSolicitud(SolicitudRecuperacion solicitudRecuperacion) {
        if (solicitudRecuperacion.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un correo", null, null), HttpStatus.BAD_REQUEST);
        Optional<Usuario> resultado = usuarioRepository.findByCorreoAndActivoIsTrue(solicitudRecuperacion.getUsuario().getCorreo());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultado.get();
        solicitudRecuperacion.setUsuario(usuario);

        if (solicitudRecuperacion.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un código", null, null), HttpStatus.BAD_REQUEST);
        Optional<SolicitudRecuperacion> resultadoSolicitud = solicitudRecuperacionRepository.findByUsuario_Id(usuario.getId());
        if (resultadoSolicitud.isPresent()) resultadoSolicitud.get().reemplazar(solicitudRecuperacion);
        else solicitudRecuperacionRepository.save(solicitudRecuperacion);

        return new ResponseEntity<>(new Mensaje(false, "Solicitud realizada", null, null), HttpStatus.OK);
    }

    // 4.6 Validar código de recuperación
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> verificarCodigo(SolicitudRecuperacion solicitudRecuperacion) {
        if (solicitudRecuperacion.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un correo", null, null), HttpStatus.BAD_REQUEST);
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByCorreoAndActivoIsTrue(solicitudRecuperacion.getUsuario().getCorreo());
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        if (solicitudRecuperacion.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un código", null, null), HttpStatus.BAD_REQUEST);
        Optional<SolicitudRecuperacion> resultadoSolicitud = solicitudRecuperacionRepository.findByUsuario_Id(usuario.getId());
        if (resultadoSolicitud.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Código inválido", null, null), HttpStatus.BAD_REQUEST);
        SolicitudRecuperacion solicitudRecuperacionActual = resultadoSolicitud.get();

        if (solicitudRecuperacion.getCodigo() == null) return new ResponseEntity<>(new Mensaje(true, "Código de restablecimiento ausente", null, null), HttpStatus.BAD_REQUEST);
        if (Duration.between(new Date().toInstant(), solicitudRecuperacionActual.getTiempo_solicitud().toInstant().plus(duracionSolicitud, ChronoUnit.MINUTES)).toMinutes() <= 0) {
            solicitudRecuperacionRepository.delete(solicitudRecuperacionActual);
            return new ResponseEntity<>(new Mensaje(true, "Se agotó el tiempo para utilizar el código", null, null), HttpStatus.BAD_REQUEST);
        }
        System.out.println(solicitudRecuperacion.getCodigo());
        System.out.println(solicitudRecuperacionActual.getCodigo());
        if (!solicitudRecuperacion.getCodigo().equals(solicitudRecuperacionActual.getCodigo())) return new ResponseEntity<>(new Mensaje(true, "Código incorrecto", null, null), HttpStatus.BAD_REQUEST);
        if (solicitudRecuperacionActual.getTiempo_canjeado() != null) return new ResponseEntity<>(new Mensaje(true, "Este código ya fue canjeado", null, null), HttpStatus.BAD_REQUEST);

        solicitudRecuperacionActual.setTiempo_canjeado(new Date());

        return new ResponseEntity<>(new Mensaje(true, "Código canjeado", null, null), HttpStatus.BAD_REQUEST);
    }

    // 4.7 Establecer nueva contraseña
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarContrasena(SolicitudRecuperacion solicitudRecuperacion) {
        if (solicitudRecuperacion.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un correo", null, null), HttpStatus.BAD_REQUEST);
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByCorreoAndActivoIsTrue(solicitudRecuperacion.getUsuario().getCorreo());
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        if (solicitudRecuperacion.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un código", null, null), HttpStatus.BAD_REQUEST);
        Optional<SolicitudRecuperacion> resultadoSolicitud = solicitudRecuperacionRepository.findByUsuario_Id(usuario.getId());
        if (resultadoSolicitud.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Código inválido", null, null), HttpStatus.BAD_REQUEST);
        SolicitudRecuperacion solicitudRecuperacionActual = resultadoSolicitud.get();

        if (!solicitudRecuperacion.getCodigo().equals(solicitudRecuperacionActual.getCodigo())) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción", null, null), HttpStatus.BAD_REQUEST);
        if (solicitudRecuperacionActual.getTiempo_canjeado() == null) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción", null, null), HttpStatus.BAD_REQUEST);

        if (Duration.between(new Date().toInstant(), solicitudRecuperacionActual.getTiempo_canjeado().toInstant().plus(duracionSolicitud, ChronoUnit.MINUTES)).toMinutes() <= 0) {
            solicitudRecuperacionRepository.delete(solicitudRecuperacionActual);
            return new ResponseEntity<>(new Mensaje(true, "Se agotó el tiempo para cambiar la contraseña", null, null), HttpStatus.BAD_REQUEST);
        }

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;
        error = Validador.validarContrasenaUsuario(solicitudRecuperacion.getUsuario().getContrasena());
        if (error.isPresent()) errores.put("contrasena", error.get());
        else if (passwordEncoder.matches(solicitudRecuperacion.getUsuario().getContrasena(), usuario.getContrasena())) errores.put("contrasena", "Debes colocar una contraseña diferente a la anterior");
        else {
            usuario.setContrasena(passwordEncoder.encode(solicitudRecuperacion.getUsuario().getContrasena()));
            solicitudRecuperacionRepository.delete(solicitudRecuperacionActual);
        }

        return !errores.isEmpty() ? new ResponseEntity<>(new Mensaje(false, "No se pudo restablecer la contraseña", errores, null), HttpStatus.OK)
        : new ResponseEntity<>(new Mensaje(true, "Contraseña restablecida", null, null), HttpStatus.BAD_REQUEST);
    }
}
