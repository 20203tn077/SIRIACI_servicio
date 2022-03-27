package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion;

import mx.edu.utez.SIRIACI_servicio.security.jwt.JwtProvider;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/iniciosesion")
@CrossOrigin(origins = {"*"})
public class InicioSesionController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;


    @PostMapping("/")
    public ResponseEntity<Mensaje> inicioSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDTO) {
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(inicioSesionDTO.getCorreo(), inicioSesionDTO.getContrasena())
            );
        } catch (Exception e) {
            return new ResponseEntity<>(new Mensaje(true, "Correo y/o contraseña incorrectos", null, null), HttpStatus.OK);
        }

        System.out.println("2");
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
