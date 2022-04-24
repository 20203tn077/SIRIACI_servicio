package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Restablecimiento;

import mx.edu.utez.SIRIACI_servicio.controller.NotificacionesYMensajes.CorreosService;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento.SolicitudRestablecimiento;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento.SolicitudRestablecimientoRepository;
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
import java.time.LocalDateTime;
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
    SolicitudRestablecimientoRepository solicitudRestablecimientoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CorreosService correosService;

    // 4.5 Solicitar restablecimiento de contraseña
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarSolicitud(SolicitudRestablecimiento solicitudRestablecimiento) {
        if (solicitudRestablecimiento.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un correo.", null, null), HttpStatus.BAD_REQUEST);
        Optional<Usuario> resultado = usuarioRepository.findByCorreoAndActivoIsTrue(solicitudRestablecimiento.getUsuario().getCorreo());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente.", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultado.get();
        solicitudRestablecimiento.setUsuario(usuario);

        if (solicitudRestablecimiento.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un código.", null, null), HttpStatus.BAD_REQUEST);
        Optional<SolicitudRestablecimiento> resultadoSolicitud = solicitudRestablecimientoRepository.findByUsuario_Id(usuario.getId());
        if (resultadoSolicitud.isPresent()) resultadoSolicitud.get().reemplazar(solicitudRestablecimiento);
        else {
            solicitudRestablecimientoRepository.save(solicitudRestablecimiento);
        }

        new Thread(() -> {
            correosService.enviarCorreoPorRestablecimiento(usuario, solicitudRestablecimiento);
        }).start();

        return new ResponseEntity<>(new Mensaje(false, "Solicitud realizada", null, null), HttpStatus.OK);
    }

    // 4.6 Validar código de recuperación
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> verificarCodigo(SolicitudRestablecimiento solicitudRestablecimiento) {
        if (solicitudRestablecimiento.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un correo.", null, null), HttpStatus.BAD_REQUEST);
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByCorreoAndActivoIsTrue(solicitudRestablecimiento.getUsuario().getCorreo());
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente.", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        if (solicitudRestablecimiento.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un código.", null, null), HttpStatus.BAD_REQUEST);
        Optional<SolicitudRestablecimiento> resultadoSolicitud = solicitudRestablecimientoRepository.findByUsuario_Id(usuario.getId());
        if (resultadoSolicitud.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Código inválido", null, null), HttpStatus.BAD_REQUEST);
        SolicitudRestablecimiento solicitudRestablecimientoActual = resultadoSolicitud.get();

        if (solicitudRestablecimiento.getCodigo() == null) return new ResponseEntity<>(new Mensaje(true, "Código de restablecimiento ausente.", null, null), HttpStatus.BAD_REQUEST);
        if (Duration.between(LocalDateTime.now(), solicitudRestablecimientoActual.getTiempo_solicitud().plus(duracionSolicitud, ChronoUnit.MINUTES)).toMinutes() <= 0) {
            solicitudRestablecimientoRepository.delete(solicitudRestablecimientoActual);
            return new ResponseEntity<>(new Mensaje(true, "Se agotó el tiempo para utilizar el código.", null, null), HttpStatus.BAD_REQUEST);
        }
        System.out.println(solicitudRestablecimiento.getCodigo());
        System.out.println(solicitudRestablecimientoActual.getCodigo());
        if (!solicitudRestablecimiento.getCodigo().equals(solicitudRestablecimientoActual.getCodigo())) return new ResponseEntity<>(new Mensaje(true, "Código incorrecto.", null, null), HttpStatus.BAD_REQUEST);
        if (solicitudRestablecimientoActual.getTiempo_canjeado() != null) return new ResponseEntity<>(new Mensaje(true, "El código ya fue canjeado.", null, null), HttpStatus.BAD_REQUEST);

        solicitudRestablecimientoActual.setTiempo_canjeado(LocalDateTime.now());

        return new ResponseEntity<>(new Mensaje(true, "Código canjeado", null, null), HttpStatus.OK);
    }

    // 4.7 Establecer nueva contraseña
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarContrasena(SolicitudRestablecimiento solicitudRestablecimiento) {
        if (solicitudRestablecimiento.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un correo.", null, null), HttpStatus.BAD_REQUEST);
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByCorreoAndActivoIsTrue(solicitudRestablecimiento.getUsuario().getCorreo());
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente.", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        if (solicitudRestablecimiento.getUsuario().getCorreo() == null) return new ResponseEntity<>(new Mensaje(true, "Debes ingresar un código.", null, null), HttpStatus.BAD_REQUEST);
        Optional<SolicitudRestablecimiento> resultadoSolicitud = solicitudRestablecimientoRepository.findByUsuario_Id(usuario.getId());
        if (resultadoSolicitud.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Código inválido.", null, null), HttpStatus.BAD_REQUEST);
        SolicitudRestablecimiento solicitudRestablecimientoActual = resultadoSolicitud.get();

        if (!solicitudRestablecimiento.getCodigo().equals(solicitudRestablecimientoActual.getCodigo())) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción.", null, null), HttpStatus.BAD_REQUEST);
        if (solicitudRestablecimientoActual.getTiempo_canjeado() == null) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción.", null, null), HttpStatus.BAD_REQUEST);

        if (Duration.between(LocalDateTime.now(), solicitudRestablecimientoActual.getTiempo_canjeado().plus(duracionSolicitud, ChronoUnit.MINUTES)).toMinutes() <= 0) {
            solicitudRestablecimientoRepository.delete(solicitudRestablecimientoActual);
            return new ResponseEntity<>(new Mensaje(true, "Se agotó el tiempo para cambiar la contraseña.", null, null), HttpStatus.BAD_REQUEST);
        }

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;
        error = Validador.validarContrasenaUsuario(solicitudRestablecimiento.getUsuario().getContrasena());
        if (error.isPresent()) errores.put("contrasena", error.get());
        else if (passwordEncoder.matches(solicitudRestablecimiento.getUsuario().getContrasena(), usuario.getContrasena())) errores.put("contrasena", "Debes colocar una contraseña diferente a la anterior.");
        else {
            usuario.setContrasena(passwordEncoder.encode(solicitudRestablecimiento.getUsuario().getContrasena()));
            solicitudRestablecimientoRepository.delete(solicitudRestablecimientoActual);
        }

        return !errores.isEmpty() ? new ResponseEntity<>(new Mensaje(false, "No se pudo restablecer la contraseña.", errores, null), HttpStatus.BAD_REQUEST)
        : new ResponseEntity<>(new Mensaje(true, "Contraseña restablecida", null, null), HttpStatus.OK);
    }
}
