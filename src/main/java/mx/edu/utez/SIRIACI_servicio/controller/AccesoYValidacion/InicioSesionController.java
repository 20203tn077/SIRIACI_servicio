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
@RequestMapping("/api/public")
@CrossOrigin(origins = {"*"})
public class InicioSesionController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider provider;


    @PostMapping("/iniciosesion")
    public ResponseEntity<Mensaje> inicioSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDTO, BindingResult result) {
        if (result.hasErrors())
            return new ResponseEntity<>(new Mensaje(true, "Usuario y/o contraseña incorrectos", null, null), HttpStatus.BAD_REQUEST);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(inicioSesionDTO.getCorreo(), inicioSesionDTO.getContrasena())
        );
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
