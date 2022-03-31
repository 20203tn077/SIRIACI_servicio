package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion;

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

import java.util.HashMap;
import java.util.Map;

@Service
public class InicioSesionService {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;
    @Autowired
    IntentoInicioSesionService intentoInicioSesionService;

    // 4.1 Iniciar sesión
    public ResponseEntity<Mensaje> iniciarSesion(String correo, String contrasena) {
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
                return new ResponseEntity<>(new Mensaje(true, "Correo y/o contraseña incorrectos: " + e.getMessage(), null, null), HttpStatus.OK);
        }

        intentoInicioSesionService.inicioExitoso(correo);
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
