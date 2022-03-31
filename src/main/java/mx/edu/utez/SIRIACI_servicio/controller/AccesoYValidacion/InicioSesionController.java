package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion;

import mx.edu.utez.SIRIACI_servicio.security.IntentoInicioSesionService;
import mx.edu.utez.SIRIACI_servicio.security.jwt.JwtProvider;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    InicioSesionService service;

    // 4.1 Iniciar sesión
    @PostMapping("/")
    public ResponseEntity<Mensaje> iniciarSesion(@Valid @RequestBody InicioSesionDTO inicioSesionDTO) {
        return service.iniciarSesion(inicioSesionDTO.getCorreo(), inicioSesionDTO.getContrasena());
    }
}
