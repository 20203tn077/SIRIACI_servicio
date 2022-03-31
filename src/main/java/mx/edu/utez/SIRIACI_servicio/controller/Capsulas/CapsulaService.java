package mx.edu.utez.SIRIACI_servicio.controller.Capsulas;

import mx.edu.utez.SIRIACI_servicio.controller.Usuarios.UsuarioController;
import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.capsula.CapsulaRepository;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsulaRepository;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import mx.edu.utez.SIRIACI_servicio.util.Validador;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class CapsulaService {
    @Autowired
    CapsulaRepository capsulaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    ImagenCapsulaRepository imagenCapsulaRepository;

    // 3.1 Consultar cápsulas informativas
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulas(Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, null, null, capsulaRepository.findAllByActivoIsTrue(pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulas(Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByActivoIsTrueAndTituloContains(filtro, pageable)), HttpStatus.OK);
    }

    // 3.2 Consultar cápsula informativa
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsula(long id) {
        Optional<Capsula> capsula = capsulaRepository.findByIdAndActivoIsTrue(id);
        if (capsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente", null, null), HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(new Mensaje(true, "OK", null, capsula.get()), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulaAdministrador(long id) {
        Optional<Capsula> capsula = capsulaRepository.findById(id);
        if (capsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente", null, null), HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(new Mensaje(true, "OK", null, capsula.get()), HttpStatus.OK);
    }

    // 3.3 Registrar cápsula informativa
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarCapsula(Capsula capsula, List<ImagenCapsula> imagenes) {
        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        error = Validador.validarTituloCapsula(capsula.getTitulo());
        if (error.isPresent()) errores.put("titulo", error.get());

        error = Validador.validarContenidoCapsula(capsula.getContenido());
        if (error.isPresent()) errores.put("contenido", error.get());

        Optional<Usuario> usuario = usuarioRepository.findByIdAndActivoIsTrue(capsula.getUsuario().getId());
        if (usuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        else capsula.setUsuario(usuario.get());

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar la cápsula", errores, null), HttpStatus.BAD_REQUEST);

        capsula = capsulaRepository.save(capsula);

        for (ImagenCapsula imagen : imagenes) {
            imagen.setCapsula(capsula);
            imagenCapsulaRepository.save(imagen);
        }

        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsula), HttpStatus.OK);
    }

    // 3.4 Consultar cápsulas informativas realizadas
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadas(long id, Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByActivoIsTrueAndUsuario_Id(id, pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadas(long id, Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByActivoIsTrueAndUsuario_IdAndTituloContains(id, filtro, pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadasAdministrador(Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAll(pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerCapsulasRealizadasAdministrador(Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsulaRepository.findAllByTituloContains(filtro, pageable)), HttpStatus.OK);
    }

    // 3.5 Modificar cápsula informativa
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarCapsula(Capsula capsula, List<ImagenCapsula> imagenesRegistrar, List<ImagenCapsula> imagenesEliminar) {
        Capsula capsulaActual = null;
        Optional<Capsula> resultadoCapsula = capsulaRepository.findByIdAndActivoIsTrue(capsula.getId());
        if (resultadoCapsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente", null, null), HttpStatus.BAD_REQUEST);
        else capsulaActual = resultadoCapsula.get();

        Usuario usuario = null;
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(capsula.getUsuario().getId());
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        else usuario = resultadoUsuario.get();

        if (capsulaActual.getUsuario().getId() != capsula.getUsuario().getId()) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción", null, null), HttpStatus.UNAUTHORIZED);

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        if (capsula.getTitulo() != null) {
            error = Validador.validarTituloCapsula(capsula.getTitulo());
            if (error.isPresent()) errores.put("titulo", error.get());
        }

        if (capsula.getContenido() != null) {
            error = Validador.validarContenidoCapsula(capsula.getContenido());
            if (error.isPresent()) errores.put("contenido", error.get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar la cápsula", errores, null), HttpStatus.BAD_REQUEST);

        capsulaActual.actualizar(capsula);

        for (ImagenCapsula imagen : imagenesRegistrar) {
            imagen.setCapsula(capsulaActual);
            imagenCapsulaRepository.save(imagen);
        }

        for (ImagenCapsula imagen : imagenesEliminar) {
            imagenCapsulaRepository.deleteById(imagen.getId());
        }

        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsula), HttpStatus.OK);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarCapsulaAdministrador(Capsula capsula, List<ImagenCapsula> imagenesRegistrar, List<ImagenCapsula> imagenesEliminar) {
        Capsula capsulaActual = null;
        Optional<Capsula> resultadoCapsula = capsulaRepository.findByIdAndActivoIsTrue(capsula.getId());
        if (resultadoCapsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente", null, null), HttpStatus.BAD_REQUEST);
        else capsulaActual = resultadoCapsula.get();

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        if (capsula.getTitulo() != null) {
            error = Validador.validarTituloCapsula(capsula.getTitulo());
            if (error.isPresent()) errores.put("titulo", error.get());
        }

        if (capsula.getContenido() != null) {
            error = Validador.validarContenidoCapsula(capsula.getContenido());
            if (error.isPresent()) errores.put("contenido", error.get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar la cápsula", errores, null), HttpStatus.BAD_REQUEST);

        capsulaActual.actualizar(capsula);

        for (ImagenCapsula imagen : imagenesRegistrar) {
            imagen.setCapsula(capsulaActual);
            imagenCapsulaRepository.save(imagen);
        }

        for (ImagenCapsula imagen : imagenesEliminar) {
            imagenCapsulaRepository.deleteById(imagen.getId());
        }

        return new ResponseEntity<>(new Mensaje(false, "OK", null, capsula), HttpStatus.OK);
    }

    // 3.6 Eliminar cápsula informativa
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> eliminarCapsula(long id, long idUsuario) {
        Capsula capsula = null;
        Optional<Capsula> resultadoCapsula = capsulaRepository.findByIdAndActivoIsTrue(id);
        if (resultadoCapsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente", null, null), HttpStatus.BAD_REQUEST);
        else capsula = resultadoCapsula.get();

        Usuario usuario = null;
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(idUsuario);
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        else usuario = resultadoUsuario.get();

        if (usuario.getId() != capsula.getUsuario().getId()) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción", null, null), HttpStatus.UNAUTHORIZED);

        capsula.setActivo(false);
        return new ResponseEntity<>(new Mensaje(false, "Incidencia eliminada", null, capsula), HttpStatus.OK);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> eliminarCapsulaAdministrador(long id) {
        Capsula capsula = null;
        Optional<Capsula> resultadoCapsula = capsulaRepository.findByIdAndActivoIsTrue(id);
        if (resultadoCapsula.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Cápsula inexistente", null, null), HttpStatus.BAD_REQUEST);
        else capsula = resultadoCapsula.get();

        capsula.setActivo(false);
        return new ResponseEntity<>(new Mensaje(false, "Incidencia eliminada", null, capsula), HttpStatus.OK);
    }
}
