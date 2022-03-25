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
import mx.edu.utez.SIRIACI_servicio.util.ErrorFormulario;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import mx.edu.utez.SIRIACI_servicio.util.Validador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        List<ErrorFormulario> errores = new ArrayList<>();

        if (Validador.isVacio(usuario.getNombre())) errores.add(new ErrorFormulario("nombre", "No puedes dejar el campo en blanco"));
        else if (!Validador.isDentroRango(usuario.getNombre(), 64)) errores.add(new ErrorFormulario("nombre", "Campo vacío o fuera del rango"));
        else if (!Validador.isNombreValido(usuario.getNombre())) errores.add(new ErrorFormulario("nombre", "Ingresa un nombre válido"));

        if (Validador.isVacio(usuario.getApellido1())) errores.add(new ErrorFormulario("apellido1", "No puedes dejar el campo en blanco"));
        else if (!Validador.isDentroRango(usuario.getApellido1(), 32)) errores.add(new ErrorFormulario("apellido1", "Excediste el límite"));
        else if (!Validador.isNombreValido(usuario.getApellido1())) errores.add(new ErrorFormulario("apellido1", "Ingresa un apellido válido"));

        if (!usuario.getApellido2().equals("$vacio")) {
            if (Validador.isVacio(usuario.getApellido2())) errores.add(new ErrorFormulario("apellido2", "No puedes dejar el campo en blanco"));
            else if (!Validador.isNombreValido(usuario.getApellido2())) errores.add(new ErrorFormulario("apellido2", "Ingresa un apellido válido"));
            else if (!Validador.isDentroRango(usuario.getApellido2(), 32)) errores.add(new ErrorFormulario("apellido2", ""));
        } else usuario.setApellido2("");

        if (Validador.isVacio(usuario.getCorreo())) errores.add(new ErrorFormulario("correo", "No puedes dejar el campo en blanco"));
        else if (!Validador.isCorreoValido(usuario.getCorreo())) errores.add(new ErrorFormulario("correo", "Excediste el límite"));
        else if (usuarioRepository.existsByCorreoAndActivoIsTrue(usuario.getCorreo())) errores.add(new ErrorFormulario("correo", "El correo ingresado ya se encuentra registrado"));

        if (Validador.isVacio(usuario.getTelefono())) errores.add(new ErrorFormulario("telefono", "No puedes dejar el campo en blanco"));
        else if (!Validador.isDentroRango(usuario.getTelefono(), 10)) errores.add(new ErrorFormulario("telefono", "Excediste el límite"));

        if (Validador.isVacio(usuario.getContrasena())) errores.add(new ErrorFormulario("contraseña", "No puedes dejar el campo en blanco"));
        else if (!Validador.isDentroRango(usuario.getContrasena(), 8, 32)) errores.add(new ErrorFormulario("contrasena", "Excediste el límite"));

        boolean isAdministrador = administrador != null;
        boolean isResponsable = responsable != null;
        boolean isEstudiante = Validador.isCorreoEstudiante(usuario.getCorreo());
        boolean isComunidadUtez = Validador.isCorreoInstitucional(usuario.getCorreo());
        usuario.setComunidadUtez(isComunidadUtez);

        if (isAdministrador && !isComunidadUtez) errores.add(new ErrorFormulario("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser administrador"));
        if (isResponsable && !isComunidadUtez) errores.add(new ErrorFormulario("rol", "El usuario debe ser parte de la comunidad UTEZ para ser responsable de aspecto"));

        if (isResponsable) {
            Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
            if (aspecto.isEmpty()) errores.add(new ErrorFormulario("aspecto", "El aspecto seleccionado no existe"));
            else responsable.setAspecto(aspecto.get());
        }

        if (isEstudiante) {
            Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
            if (carrera.isEmpty()) errores.add(new ErrorFormulario("carrera", "La carrera seleccionada no existe"));
            else estudiante.setCarrera(carrera.get());

            if (!Validador.isLetraValida(estudiante.getGrupo())) errores.add(new ErrorFormulario("carrera", "El grupo ingresado no es válido"));
            if (!Validador.isDentroRango(estudiante.getCuatrimestre(), 1, 11)) errores.add(new ErrorFormulario("cuatrimestre", "El grupo ingresado no es válido"));
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar al usuario", errores, null), HttpStatus.BAD_REQUEST);

        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
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
