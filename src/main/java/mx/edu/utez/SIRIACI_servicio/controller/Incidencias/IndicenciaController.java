package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.security.DetalleUsuario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/incidencias")
@CrossOrigin(origins = {"*"})
public class IndicenciaController {
    private final static Logger logger = LoggerFactory.getLogger(UsuarioController.class);
    @Autowired
    IncidenciaService service;

    @PostMapping("/")
    public ResponseEntity<Mensaje> registrarIncidencia(@RequestBody IncidenciaDTO incidenciaDTO) {
        DetalleUsuario usuario = null;

        try {
            usuario = (DetalleUsuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (ClassCastException e) {
            logger.error("Error en método automodificacion" + e.getMessage());
            return new ResponseEntity<>(new Mensaje(true, "Error de autenticación", null, null), HttpStatus.BAD_REQUEST);
        }

        //try {
            List<ImagenIncidencia> imagenes = new ArrayList<>();
            for (String imagen : incidenciaDTO.getImagenesIncidencia()) {
                imagenes.add(new ImagenIncidencia(Base64.getDecoder().decode(imagen)));
            }
            return service.registrarIncidencia(new Incidencia(
                            incidenciaDTO.getDescripcion(),
                            new Date(),
                            incidenciaDTO.getLongitud(),
                            incidenciaDTO.getLatitud(),
                            new Importancia(incidenciaDTO.getImportancia()),
                            new Aspecto(incidenciaDTO.getAspecto()),
                            new Usuario(usuario.getId())
                    ),
                    imagenes
            );
        //} catch (Exception e) {
        //    logger.error("Error en método " + e.getMessage());
        //    return new ResponseEntity<>(new Mensaje(true, "Error al ", null, null), HttpStatus.BAD_REQUEST);
        //}
    }
}
