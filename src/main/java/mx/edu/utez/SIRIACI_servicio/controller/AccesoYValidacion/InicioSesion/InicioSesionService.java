package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.InicioSesion;

import mx.edu.utez.SIRIACI_servicio.controller.NotificacionesYMensajes.NotificacionesService;
import mx.edu.utez.SIRIACI_servicio.model.dispositivoMovil.DispositivoMovil;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.security.IntentoInicioSesionService;
import mx.edu.utez.SIRIACI_servicio.security.jwt.JwtProvider;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class InicioSesionService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;
    @Autowired
    IntentoInicioSesionService intentoInicioSesionService;
    @Autowired
    NotificacionesService notificacionesService;

    // 4.1 Iniciar sesión
    public ResponseEntity<Mensaje> iniciarSesion(String correo, String contrasena, String dispositivoMovil) {
        Authentication authentication = null;
        try {
            try {
                intentoInicioSesionService.verificarExistencia(correo);
                intentoInicioSesionService.verificarBloqueo(correo);
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(correo, contrasena)
                );
            } catch (BadCredentialsException e) {
                intentoInicioSesionService.inicioFallido(correo);
            }
        }catch (Exception e) {
            if (e.getMessage().startsWith("MSJFRONT ")) {
                return new ResponseEntity<>(new Mensaje(true, e.getMessage().replaceFirst("MSJFRONT ", ""), null, null), HttpStatus.OK);
            } else
                return new ResponseEntity<>(new Mensaje(true, "Correo y/o contraseña incorrectos", null, null), HttpStatus.OK);
        }

        Usuario usuario = intentoInicioSesionService.inicioExitoso(correo);
        if (dispositivoMovil != null) notificacionesService.suscribirse(new DispositivoMovil(
                dispositivoMovil,
                LocalDateTime.now(),
                usuario
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = provider.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Map<String, Object> datos = new HashMap<>();
        datos.put("token", token);
        datos.put("correo", userDetails.getUsername());
        datos.put("roles", userDetails.getAuthorities());
        return new ResponseEntity<>(new Mensaje(false, "OK", null, datos), HttpStatus.OK);
    }
}
