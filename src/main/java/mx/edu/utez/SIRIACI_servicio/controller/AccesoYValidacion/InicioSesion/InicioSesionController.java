package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.InicioSesion;

import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
