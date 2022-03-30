package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.estado.EstadoRepository;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.ImportanciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.IncidenciaRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import mx.edu.utez.SIRIACI_servicio.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
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
public class IncidenciaService {
    @Autowired
    IncidenciaRepository incidenciaRepository;
    @Autowired
    AspectoRepository aspectoRepository;
    @Autowired
    ImportanciaRepository importanciaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    EstadoRepository estadoRepository;
    @Autowired
    ImagenIncidenciaRepository imagenIncidenciaRepository;


    // 2.1 Registrar nuevo reporte de incidencia
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarIncidencia(Incidencia incidencia, List<ImagenIncidencia> imagenes) {
        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        error = Validador.validarDescripcionIncidencia(incidencia.getDescripcion());
        if (error.isPresent()) errores.put("descripcion", error.get());

        error = Validador.validarUbicacionIncidencia(incidencia.getLatitud(), incidencia.getLongitud());
        if (error.isPresent()) errores.put("ubicacion", error.get());

        Optional<Aspecto> aspecto = aspectoRepository.findById(incidencia.getAspecto().getId());
        if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionada no existe");
        else incidencia.setAspecto(aspecto.get());

        Optional<Importancia> importancia = importanciaRepository.findById(incidencia.getImportancia().getId());
        if (importancia.isEmpty()) errores.put("importancia", "El nivel de importancia seleccionada no existe");
        else incidencia.setImportancia(importancia.get());

        Optional<Usuario> usuario = usuarioRepository.findByIdAndActivoIsTrue(incidencia.getUsuario().getId());
        if (usuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        else incidencia.setUsuario(usuario.get());

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar la incidencia", errores, null), HttpStatus.BAD_REQUEST);

        incidencia.setEstado(estadoRepository.findFirstByOrderById());

        incidencia = incidenciaRepository.save(incidencia);

        for (ImagenIncidencia imagen : imagenes) {
            imagen.setIncidencia(incidencia);
            imagenIncidenciaRepository.save(imagen);
        }

        return new ResponseEntity<>(new Mensaje(false, "Incidencia registrada", null, incidencia), HttpStatus.OK);

    }

    // 2.2 Consultar reportes de incidencia realizados
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidenciasRealizadas(long idUsuario, Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByActivoIsTrueAndUsuario_Id(idUsuario, pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidenciasRealizadas(long idUsuario, Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByActivoIsTrueAndDescripcionContainsAndUsuario_Id(filtro, idUsuario, pageable)), HttpStatus.OK);
    }

    // 2.3 Consultar reporte de incidencia
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidencia(long idUsuario, long id) {
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(idUsuario);
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        Optional<Incidencia> resultadoIncidencia = incidenciaRepository.findById(id);
        if (resultadoIncidencia.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        Incidencia incidencia = resultadoIncidencia.get();

        if (usuario.getAdministrador() != null) return new ResponseEntity<>(new Mensaje(false, "OK", null, incidencia), HttpStatus.OK);
        else if (incidencia.isActivo()) {
            if (
                    usuario.getResponsable() != null && usuario.getResponsable().getAspecto().getId() == incidencia.getAspecto().getId() ||
                    usuario.getId() == incidencia.getUsuario().getId()
            ) return new ResponseEntity<>(new Mensaje(false, "OK", null, incidencia), HttpStatus.OK);
            else return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para ver esto", null, null), HttpStatus.UNAUTHORIZED);
        }
        else return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Mensaje> obtenerIncidenciaResponsable(long idUsuario, long id) {
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(idUsuario);
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        Optional<Incidencia> resultadoIncidencia = incidenciaRepository.findById(id);
        if (resultadoIncidencia.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        Incidencia incidencia = resultadoIncidencia.get();

        if (usuario.getAdministrador() != null) return new ResponseEntity<>(new Mensaje(false, "OK", null, incidencia), HttpStatus.OK);
        else if (incidencia.isActivo()) {
            if (
                    usuario.getResponsable() != null && usuario.getResponsable().getAspecto().getId() == incidencia.getAspecto().getId() ||
                            usuario.getId() == incidencia.getUsuario().getId()
            ) return new ResponseEntity<>(new Mensaje(false, "OK", null, incidencia), HttpStatus.OK);
            else return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para ver esto", null, null), HttpStatus.UNAUTHORIZED);
        }
        else return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
    }
    public ResponseEntity<Mensaje> obtenerIncidenciaAdministrador(long id) {
        Optional<Incidencia> resultadoIncidencia = incidenciaRepository.findById(id);
        if (resultadoIncidencia.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidencia.get()), HttpStatus.OK);
    }

    // 2.4 Modificar reporte de incidencia
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarIncidencia(Incidencia incidencia, List<ImagenIncidencia> imagenesRegistrar, List<ImagenIncidencia> imagenesEliminar) {
        Incidencia incidenciaActual = null;
        Optional<Incidencia> resultado = incidenciaRepository.findByIdAndActivoIsTrue(incidencia.getId());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        else incidenciaActual = resultado.get();

        if (incidenciaActual.getUsuario().getId() != incidencia.getUsuario().getId()) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción", null, null), HttpStatus.UNAUTHORIZED);
        if (incidenciaActual.getEstado().getId() != estadoRepository.findFirstByOrderById().getId()) {
            if (incidenciaActual.getEstado().getId() == estadoRepository.findFirstByOrderByIdDesc().getId()) return new ResponseEntity<>(new Mensaje(true, "No puedes modificar tu reporte, la incidencia ya fue atendida", null, null), HttpStatus.BAD_REQUEST);
            else return new ResponseEntity<>(new Mensaje(true, "No puedes modificar tu reporte, la incidencia ya está siendo atendida", null, null), HttpStatus.BAD_REQUEST);
        }

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        if (incidencia.getDescripcion() != null) {
            error = Validador.validarDescripcionIncidencia(incidencia.getDescripcion());
            if (error.isPresent()) errores.put("descripcion", error.get());
        }

        if (incidencia.getLatitud() != null && incidencia.getLongitud() != null) {
            error = Validador.validarUbicacionIncidencia(incidencia.getLatitud(), incidencia.getLongitud());
            if (error.isPresent()) errores.put("ubicacion", error.get());
        }

        if (incidencia.getAspecto().getId() != null) {
            Optional<Aspecto> aspecto = aspectoRepository.findById(incidencia.getAspecto().getId());
            if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionada no existe");
            else incidencia.setAspecto(aspecto.get());
        }


        if (incidencia.getImportancia().getId() != null) {
            Optional<Importancia> importancia = importanciaRepository.findById(incidencia.getImportancia().getId());
            if (importancia.isEmpty()) errores.put("importancia", "El nivel de importancia seleccionada no existe");
            else incidencia.setImportancia(importancia.get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar la incidencia", errores, null), HttpStatus.BAD_REQUEST);

        incidenciaActual.actualizar(incidencia);

        for (ImagenIncidencia imagen : imagenesRegistrar) {
            imagen.setIncidencia(incidenciaActual);
            imagenIncidenciaRepository.save(imagen);
        }

        for (ImagenIncidencia imagen : imagenesEliminar) {
            imagenIncidenciaRepository.deleteById(imagen.getId());
        }

        return new ResponseEntity<>(new Mensaje(false, "Incidencia actualizada", null, incidencia), HttpStatus.OK);
    }

    // 2.5 Consultar reportes de incidencia
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidenciasAdministrador(long idUsuario, Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAll(pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidenciasAdministrador(long idUsuario, Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByDescripcionContains(filtro, pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidenciasResponsable(long idUsuario, Pageable pageable) {
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(idUsuario);
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByActivoIsTrueAndAspecto_Id(usuario.getResponsable().getAspecto().getId(), pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerIncidenciasResponsable(long idUsuario, Pageable pageable, String filtro) {
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(idUsuario);
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();
        return new ResponseEntity<>(new Mensaje(false, "OK", null, incidenciaRepository.findAllByActivoIsTrueAndDescripcionContainsAndAspecto_Id(filtro, usuario.getResponsable().getAspecto().getId(), pageable)), HttpStatus.OK);
    }


    // 2.6 Atender reporte de incidencia
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> atenderIncidenciaAdministrador(Incidencia incidencia) {
        Incidencia incidenciaActual = null;
        Optional<Incidencia> resultado = incidenciaRepository.findByIdAndActivoIsTrue(incidencia.getId());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        else incidenciaActual = resultado.get();

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        if (incidenciaActual.getEstado().getId() == estadoRepository.findFirstByOrderByIdDesc().getId()) return new ResponseEntity<>(new Mensaje(true, "La incidencia ya fue atendida", null, null), HttpStatus.BAD_REQUEST);

        if (incidencia.getComentario() != null) {
            error = Validador.validarComentarioIncidencia(incidencia.getComentario());
            if (error.isPresent()) errores.put("comentario", error.get());
        }

        if (incidenciaActual.getEstado().getId() != null) {
            incidencia.setEstado(estadoRepository.findById((byte)(incidenciaActual.getEstado().getId() + 1)).get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo atender la incidencia", errores, null), HttpStatus.BAD_REQUEST);

        incidenciaActual.atender(incidencia);

        return new ResponseEntity<>(new Mensaje(false, "Incidencia atendida", null, incidencia), HttpStatus.OK);
    }
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> atenderIncidenciaResponsable(Incidencia incidencia) {
        Incidencia incidenciaActual = null;
        Optional<Incidencia> resultado = incidenciaRepository.findByIdAndActivoIsTrue(incidencia.getId());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        else incidenciaActual = resultado.get();
        Optional<Usuario> resultadoUsuario = usuarioRepository.findByIdAndActivoIsTrue(incidencia.getUsuario().getId());
        if (resultadoUsuario.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultadoUsuario.get();

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        if (incidenciaActual.getAspecto().getId() != usuario.getResponsable().getAspecto().getId()) return new ResponseEntity<>(new Mensaje(true, "No tienes permiso para realizar esta acción", null, null), HttpStatus.UNAUTHORIZED);
        if (incidenciaActual.getEstado().getId() == estadoRepository.findFirstByOrderByIdDesc().getId()) return new ResponseEntity<>(new Mensaje(true, "La incidencia ya fue atendida", null, null), HttpStatus.BAD_REQUEST);

        if (incidencia.getComentario() != null) {
            error = Validador.validarComentarioIncidencia(incidencia.getComentario());
            if (error.isPresent()) errores.put("comentario", error.get());
        }

        if (incidenciaActual.getEstado().getId() != null) {
            incidencia.setEstado(estadoRepository.findById((byte)(incidenciaActual.getEstado().getId() + 1)).get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo atender la incidencia", errores, null), HttpStatus.BAD_REQUEST);

        incidenciaActual.atender(incidencia);

        return new ResponseEntity<>(new Mensaje(false, "Incidencia atendida", null, incidencia), HttpStatus.OK);
    }

    // 2.7 Eliminar reporte de incidencia
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> eliminarIncidencia(long id) {
        Optional<Incidencia> incidencia = incidenciaRepository.findByIdAndActivoIsTrue(id);
        if (incidencia.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
        incidencia.get().setActivo(false);
        return new ResponseEntity<>(new Mensaje(true, "Incidencia inexistente", null, null), HttpStatus.BAD_REQUEST);
    }
}
