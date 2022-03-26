package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.administrador.AdministradorRepository;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.AspectoRepository;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.carrera.CarreraRepository;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.EstudianteRepository;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificado;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificadoRepository;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.responsable.ResponsableRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import mx.edu.utez.SIRIACI_servicio.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Service
@Transactional
public class UsuarioService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    NoVerificadoRepository noVerificadoRepository;
    @Autowired
    AspectoRepository aspectoRepository;
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    ResponsableRepository responsableRepository;
    @Autowired
    CarreraRepository carreraRepository;
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> registrarUsuario(Usuario usuario, Administrador administrador, Responsable responsable, Estudiante estudiante) {
        Map<String, String> errores = new HashMap<>();
        Optional<String> error;
        boolean isEstudiante = false;
        boolean isComunidadUtez = false;

        error = Validador.validarNombreUsuario(usuario.getNombre());
        if (error.isPresent()) errores.put("nombre", error.get());

        error = Validador.validarApellidoUsuario(usuario.getApellido1());
        if (error.isPresent()) errores.put("apellido1", error.get());

        if (usuario.getApellido2() != null) {
            error = Validador.validarApellidoUsuario(usuario.getApellido2());
            if (error.isPresent()) errores.put("apellido2", error.get());
        } else usuario.setApellido2("");

        error = Validador.validarCorreoUsuario(usuario.getCorreo());
        if (error.isPresent()) errores.put("correo", error.get());
        else if (usuarioRepository.existsByCorreoAndActivoIsTrue(usuario.getCorreo())) errores.put("correo", "El correo ingresado ya se encuentra registrado");
        else {
            isEstudiante = Validador.isCorreoEstudiante(usuario.getCorreo());
            isComunidadUtez = Validador.isCorreoInstitucional(usuario.getCorreo());
            usuario.setComunidadUtez(isComunidadUtez);
        }

        error = Validador.validarTelefonoUsuario(usuario.getTelefono());
        if (error.isPresent()) errores.put("telefono", error.get());

        error = Validador.validarContrasenaUsuario(usuario.getContrasena());
        if (error.isPresent()) errores.put("contrasena", error.get());
        else usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        boolean isAdministrador = administrador != null;
        boolean isResponsable = responsable != null;

        if (isAdministrador && !isComunidadUtez) errores.put("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser administrador");
        if (isResponsable && !isComunidadUtez) errores.put("rolResponsable", "El usuario debe ser parte de la comunidad UTEZ para ser responsable de aspecto");

        if (isResponsable) {
            Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
            if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
            else responsable.setAspecto(aspecto.get());
        }

        if (isEstudiante) {
            Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
            if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
            else estudiante.setCarrera(carrera.get());

            error = Validador.validarCuatrimestreUsuario(estudiante.getCuatrimestre());
            if (error.isPresent()) errores.put("cuatrimestre", error.get());

            error = Validador.validarGrupoUsuario(estudiante.getGrupo());
            if (error.isPresent()) errores.put("grupo", error.get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar al usuario", errores, null), HttpStatus.BAD_REQUEST);

        usuario = usuarioRepository.save(usuario);

        if (isAdministrador) {
            administrador.setUsuario(usuario);
            administradorRepository.save(administrador);
        }
        if (isResponsable) {
            responsable.setUsuario(usuario);
            responsableRepository.save(responsable);
        }
        if (isEstudiante) {
            estudiante.setUsuario(usuario);
            estudianteRepository.save(estudiante);
        }
        noVerificadoRepository.save(new NoVerificado(
                UUID.randomUUID().toString(),
                usuario
        ));

        return new ResponseEntity<>(new Mensaje(false, "Usuario registrado", null, usuario), HttpStatus.OK);
    }

    /*@Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> autoregistro() {

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarUsuario() {

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> automodificacion() {

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> eliminarUsuario() {

    }*/
    /*
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> entrada() {

    }

    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> salida() {

    }
    */
}
