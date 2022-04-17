package mx.edu.utez.SIRIACI_servicio.security;

import mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Bloqueos.BloqueosService;
import mx.edu.utez.SIRIACI_servicio.model.bloqueo.Bloqueo;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class IntentoInicioSesionService {

    @Value("${conf.intentos_inicio_sesion}")
    private int numeroIntentos;
    @Value("${conf.duracion_bloqueo}")
    private int duracionBloqueo;

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    BloqueosService bloqueosService;

    public void inicioExitoso(String correo) {
        Usuario usuario = usuarioRepository.findByCorreoAndActivoIsTrue(correo).get();
        usuario.reiniciarIntentosFallidos();
        usuarioRepository.save(usuario);
    }

    public String inicioFallido(String correo) {
        Usuario usuario = usuarioRepository.findByCorreoAndActivoIsTrue(correo).get();
        if (usuario.getIntentosFallidos() < numeroIntentos - 1) {
            usuario.aumentarIntentosFallidos();
            usuarioRepository.save(usuario);
            int intentosRestantes = numeroIntentos - usuario.getIntentosFallidos();
            throw new RuntimeException("MSJFRONT Contraseña incorrecta, " + intentosRestantes + (intentosRestantes == 1 ? " intento restante" : " intentos restantes"));
        } else {
            bloqueosService.bloquear(usuario);
            usuario.reiniciarIntentosFallidos();
            usuarioRepository.save(usuario);
            throw new RuntimeException("MSJFRONT Contraseña incorrecta, tu cuenta ha sido bloqueada temporalmente");
        }
    }

    public void verificarBloqueo(String correo) {
        Bloqueo bloqueo = usuarioRepository.findByCorreoAndActivoIsTrue(correo).get().getBloqueo();
        if (bloqueo != null) {
            long minutosRestantes = Duration.between(LocalDateTime.now(), bloqueo.getTiempoBloqueo().plus(duracionBloqueo, ChronoUnit.MINUTES)).toMinutes();
            if (minutosRestantes > 0) {
                throw new RuntimeException("MSJFRONT Tu cuenta se encuentra bloqueada, vuelve a intentarlo en " + minutosRestantes + " minuto" + (minutosRestantes == 1 ? "" : "s"));
            } else {
                bloqueosService.desbloquear(usuarioRepository.findByCorreoAndActivoIsTrue(correo).get());
            }
        }
    }

    public void verificarExistencia(String correo) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreoAndActivoIsTrue(correo);
        if (usuario.isEmpty()) throw new RuntimeException("MSJFRONT Usuario no encontrado");
    }
}
