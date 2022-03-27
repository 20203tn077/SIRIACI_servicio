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
    public ResponseEntity<Mensaje> registrarUsuario(Usuario usuario, Administrador administrador, Responsable responsable, Estudiante estudiante, Boolean isAdministrador, Boolean isResponsable) {
        Map<String, String> errores = new HashMap<>();
        Optional<String> error;
        boolean isEstudiante = false;
        boolean isComunidadUtez = false;

        error = Validador.validarNombreUsuario(usuario.getNombre());
        if (error.isPresent()) errores.put("nombre", error.get());

        error = Validador.validarApellido1Usuario(usuario.getApellido1());
        if (error.isPresent()) errores.put("apellido1", error.get());


        error = Validador.validarApellido2Usuario(usuario.getApellido2());
        if (error.isPresent()) errores.put("apellido2", error.get());

        error = Validador.validarCorreoUsuario(usuario.getCorreo());
        if (error.isPresent()) errores.put("correo", error.get());
        else {
            if (usuarioRepository.existsByCorreoAndActivoIsTrue(usuario.getCorreo())) errores.put("correo", "El correo ingresado ya se encuentra registrado");
            isEstudiante = Validador.isCorreoEstudiante(usuario.getCorreo());
            isComunidadUtez = Validador.isCorreoInstitucional(usuario.getCorreo());
            usuario.setComunidadUtez(isComunidadUtez);
        }

        error = Validador.validarTelefonoUsuario(usuario.getTelefono());
        if (error.isPresent()) errores.put("telefono", error.get());

        error = Validador.validarContrasenaUsuario(usuario.getContrasena());
        if (error.isPresent()) errores.put("contrasena", error.get());
        else usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        if (isAdministrador == null) errores.put("rolAdministrador", "Debes indicar si el usuario es un administrador o no");
        else if (isAdministrador && !isComunidadUtez) errores.put("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser administrador");

        if (isResponsable == null) errores.put("rolAdministrador", "Debes indicar si el usuario es un responsable de aspecto o no");
        else {
            if (isResponsable && !isComunidadUtez) errores.put("rolResponsable", "El usuario debe ser parte de la comunidad UTEZ para ser responsable de aspecto");
            if (isResponsable) {
                Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                else responsable.setAspecto(aspecto.get());
            }
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

    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerUsuario(long id) {
        Optional<Usuario> resultado = usuarioRepository.findByIdAndActivoIsTrue(id);
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "El usuario no existe", null, null), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Mensaje(false, "OK", null, resultado.get()), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarUsuario(Usuario usuario, Administrador administrador, Responsable responsable, Estudiante estudiante, Boolean isAdministrador, Boolean isResponsable) {
        Optional<Usuario> resultado = usuarioRepository.findByIdAndActivoIsTrue(usuario.getId());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuarioActual = resultado.get();
        boolean isEstudianteActual = usuarioActual.getEstudiante() != null;
        boolean isResponsableActual = usuarioActual.getResponsable() != null;
        boolean isAdministradorActual = usuarioActual.getAdministrador() != null;

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;
        boolean isEstudiante = isEstudianteActual;
        boolean isComunidadUtez = usuarioActual.isComunidadUtez();

        if (usuario.getNombre() != null) {
            error = Validador.validarNombreUsuario(usuario.getNombre());
            if (error.isPresent()) errores.put("nombre", error.get());
        }

        if (usuario.getApellido1() != null) {
            error = Validador.validarApellido1Usuario(usuario.getApellido1());
            if (error.isPresent()) errores.put("apellido1", error.get());
        }

        if (usuario.getApellido2()!= null) {
            error = Validador.validarApellido2Usuario(usuario.getApellido2());
            if (error.isPresent()) errores.put("apellido2", error.get());
        }


        if (usuario.getCorreo() != null) {
            error = Validador.validarCorreoUsuario(usuario.getCorreo());
            if (error.isPresent()) errores.put("correo", error.get());
            else{
                if (usuarioRepository.existsByCorreoAndActivoIsTrue(usuario.getCorreo())) errores.put("correo", "El correo ingresado ya se encuentra registrado");
                isEstudiante = Validador.isCorreoEstudiante(usuario.getCorreo());
                isComunidadUtez = Validador.isCorreoInstitucional(usuario.getCorreo());
                usuario.setComunidadUtez(isComunidadUtez);
            }
        }

        if (usuario.getTelefono() != null) {
            error = Validador.validarTelefonoUsuario(usuario.getTelefono());
            if (error.isPresent()) errores.put("telefono", error.get());
        }

        if (usuario.getContrasena() != null) {
            error = Validador.validarContrasenaUsuario(usuario.getContrasena());
            if (error.isPresent()) errores.put("contrasena", error.get());
            else usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        if (isAdministrador == null) {if (isAdministradorActual && !isComunidadUtez) errores.put("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser administrador");}
        else if (isAdministrador && !isComunidadUtez) errores.put("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser admisdfnistrador");

        if (isResponsable == null) {
            if (isResponsableActual && !isComunidadUtez) errores.put("rolResponsable", "El usuario debe ser parte de la comunidad UTEZ para ser responsable de aspecto");
            if (isResponsableActual) {
                if (responsable.getAspecto().getId() != null) {
                    Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                    if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                    else responsable.setAspecto(aspecto.get());
                }
            }
        } else {
            if (isResponsable && !isComunidadUtez) errores.put("rolResponsable", "El usuario debe ser parte de la comunidad UTEZ para ser responsable de aspecto");
            if (isResponsable) {
                Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                else responsable.setAspecto(aspecto.get());
            }
        }

        if (isEstudiante == isEstudianteActual) {
            if (isEstudianteActual) {
                if (estudiante.getCarrera().getId() != null) {
                    Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                    if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                    else estudiante.setCarrera(carrera.get());
                }

                if (estudiante.getCuatrimestre() != null) {
                    error = Validador.validarCuatrimestreUsuario(estudiante.getCuatrimestre());
                    if (error.isPresent()) errores.put("cuatrimestre", error.get());
                }

                if (estudiante.getGrupo() != null) {
                    error = Validador.validarGrupoUsuario(estudiante.getGrupo());
                    if (error.isPresent()) errores.put("grupo", error.get());
                }
            }
        } else {
            if (isEstudiante) {
                Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                else estudiante.setCarrera(carrera.get());

                error = Validador.validarCuatrimestreUsuario(estudiante.getCuatrimestre());
                if (error.isPresent()) errores.put("cuatrimestre", error.get());

                error = Validador.validarGrupoUsuario(estudiante.getGrupo());
                if (error.isPresent()) errores.put("grupo", error.get());
            }
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo modificar al usuario", errores, null), HttpStatus.BAD_REQUEST);

        usuarioActual.actualizar(usuario);

        if (isAdministrador != null && isAdministrador != isAdministradorActual) {
            if (isAdministrador) {
                administrador.setUsuario(usuarioActual);
                administradorRepository.save(administrador);
            } else {
                administradorRepository.delete(usuarioActual.getAdministrador());
            }
        }

        if (isResponsable == null) {
            if (isResponsableActual) {
                usuarioActual.getResponsable().actualizar(responsable);
            }
        } else if (isResponsable != isResponsableActual) {
            if (isResponsable) {
                responsable.setUsuario(usuario);
                responsableRepository.save(responsable);
            } else {
                responsableRepository.delete(usuarioActual.getResponsable());
            }
        }

        if (isEstudiante == isEstudianteActual) {
            if (isEstudiante) {
                usuarioActual.getEstudiante().actualizar(estudiante);
            }
        } else if (isEstudiante != isEstudianteActual){
            if (isEstudiante) {
                estudiante.setUsuario(usuario);
                estudianteRepository.save(estudiante);
            } else {
                estudianteRepository.delete(usuarioActual.getEstudiante());
            }
        }

        return new ResponseEntity<>(new Mensaje(false, "Usuario actualizado", null, usuario), HttpStatus.OK);
    }

    /*
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> modificarUsuario(Usuario usuario, Administrador administrador, Responsable responsable, Estudiante estudiante, Boolean isAdministrador, Boolean isResponsable) {
        Optional<Usuario> resultado = usuarioRepository.findByIdAndActivoIsTrue(usuario.getId());
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuarioActual = new Usuario(resultado.get());
        boolean isEstudiante = false;
        boolean isComunidadUtez = usuarioActual.isComunidadUtez();

        boolean cambioAdministrador = false;
        boolean cambioResponsable = false;
        boolean cambioEstudiante = false;

        Map<String, String> errores = new HashMap<>();
        Optional<String> error;

        if (usuario.getNombre() != null) {
            error = Validador.validarNombreUsuario(usuario.getNombre());
            if (error.isPresent()) errores.put("nombre", error.get());
            else usuarioActual.setNombre(usuario.getNombre());
        }

        if (usuario.getApellido1() != null) {
            error = Validador.validarApellidoUsuario(usuario.getApellido1());
            if (error.isPresent()) errores.put("apellido1", error.get());
            else usuarioActual.setApellido1(usuario.getApellido1());
        }

        if (usuario.getApellido2() != null) {
            if (!usuario.getApellido2().equals("")) {
                error = Validador.validarApellidoUsuario(usuario.getApellido2());
                if (error.isPresent()) errores.put("apellido2", error.get());
                else usuarioActual.setApellido2(usuario.getApellido2());
            } else usuarioActual.setApellido2(usuario.getApellido2());
        }

        if (usuario.getCorreo() != null) {
            error = Validador.validarCorreoUsuario(usuario.getCorreo());
            if (error.isPresent()) errores.put("correo", error.get());
            else if (usuarioRepository.existsByCorreoAndActivoIsTrue(usuario.getCorreo())) errores.put("correo", "El correo ingresado ya se encuentra registrado");
            else {
                boolean isEstudianteNuevoCorreo = Validador.isCorreoEstudiante(usuario.getCorreo());
                if (isEstudianteNuevoCorreo != (usuarioActual.getEstudiante() != null)) {
                    isEstudiante = isEstudianteNuevoCorreo;
                    cambioEstudiante = true;
                }
                boolean isComunidadUtezNuevoCorreo = isComunidadUtez = Validador.isCorreoInstitucional(usuario.getCorreo());
                usuarioActual.setComunidadUtez(isComunidadUtez);
                usuarioActual.setCorreo(usuario.getCorreo());
            }
        }

        if (usuario.getTelefono() != null) {
            error = Validador.validarTelefonoUsuario(usuario.getTelefono());
            if (error.isPresent()) errores.put("telefono", error.get());
            else usuarioActual.setTelefono(usuario.getTelefono());
        }

        if (usuario.getContrasena() != null) {
            error = Validador.validarContrasenaUsuario(usuario.getContrasena());
            if (error.isPresent()) errores.put("contrasena", error.get());
            else usuarioActual.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        if (isAdministrador == null ) isAdministrador = usuarioActual.getAdministrador() != null;
        else if (isAdministrador != (usuarioActual.getAdministrador() != null)) {
            cambioAdministrador = true;
            if (!isAdministrador && !(administradorRepository.countByUsuario_ActivoIsTrue() > 1)) errores.put("rolAdministrador", "Debe haber al menos un administrador en el sistema");
        } else {
            administrador = usuarioActual.getAdministrador();
        }

        if (isAdministrador && !isComunidadUtez) errores.put("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser administrador");

        if (isResponsable == null) isResponsable = usuarioActual.getResponsable() != null;
        else if (isResponsable != (usuarioActual.getResponsable() != null)) {
            cambioResponsable = true;
            if (isResponsable) {
                Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                else responsable.setAspecto(aspecto.get());
            } else responsable = usuarioActual.getResponsable();
        } else {
            Responsable responsableActual = new Responsable(usuarioActual.getResponsable());
            if (responsable.getAspecto() != null) {
                Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                else responsableActual.setAspecto(aspecto.get());
            }
            responsable = responsableActual;
        }

        if (isResponsable && !isComunidadUtez) errores.put("rolResponsable", "El usuario debe ser parte de la comunidad UTEZ para ser responsable de aspecto");

        if (cambioEstudiante) {
            if (isEstudiante) {
                Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                else estudiante.setCarrera(carrera.get());

                error = Validador.validarCuatrimestreUsuario(estudiante.getCuatrimestre());
                if (error.isPresent()) errores.put("cuatrimestre", error.get());

                error = Validador.validarGrupoUsuario(estudiante.getGrupo());
                if (error.isPresent()) errores.put("grupo", error.get());
            } else {
                estudiante = usuarioActual.getEstudiante();
            }
        } else {
            Estudiante estudianteActual = new Estudiante(usuarioActual.getEstudiante());

            if (estudiante.getCarrera().getId() != null) {
                Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                else estudianteActual.setCarrera(carrera.get());
            }

            if (estudiante.getCuatrimestre() != null) {
                error = Validador.validarCuatrimestreUsuario(estudiante.getCuatrimestre());
                if (error.isPresent()) errores.put("cuatrimestre", error.get());
                else estudianteActual.setCuatrimestre(estudiante.getCuatrimestre());
            }

            if (estudiante.getGrupo() != null) {
                error = Validador.validarGrupoUsuario(estudiante.getGrupo());
                if (error.isPresent()) errores.put("grupo", error.get());
                else estudianteActual.setGrupo(estudiante.getGrupo());
            }
            estudiante = estudianteActual;
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo modificar al usuario", errores, null), HttpStatus.BAD_REQUEST);
        usuario = usuarioRepository.findByIdAndActivoIsTrue(usuario.getId()).get();
        usuario.actualizar(usuarioActual);

        if (cambioAdministrador) {
            if (isAdministrador) {
                administrador.setUsuario(usuario);
                administradorRepository.save(administrador);
            } else {
                System.out.println("hola");
                administradorRepository.delete(administrador);
            }
        }

        if (cambioResponsable) {
            if (isResponsable) {
                responsable.setUsuario(usuario);
                responsableRepository.save(responsable);
            } else {
                responsableRepository.delete(responsable);
            }
        } else if (isResponsable) {
            usuario.getResponsable().actualizar(responsable);
        }

        if (cambioEstudiante) {
            if (isEstudiante) {
                estudiante.setUsuario(usuario);
                estudianteRepository.save(estudiante);
            } else {
                estudianteRepository.delete(estudiante);
            }
        } else if (isEstudiante) {
            usuario.getEstudiante().actualizar(estudiante);
        }

        return new ResponseEntity<>(new Mensaje(false, "Usuario actualizado", null, usuario), HttpStatus.OK);
    }

     */

    /*@Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> autoregistro() {

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
