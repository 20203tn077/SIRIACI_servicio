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
import org.springframework.data.domain.Pageable;
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

    // 1.1 Registrar nuevo usuario
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

        if (usuario.getApellido2()!= null) {
            error = Validador.validarApellido2Usuario(usuario.getApellido2());
            if (error.isPresent()) errores.put("apellido2", error.get());
        }

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
                if (responsable.getAspecto().getId() == null) errores.put("aspecto", "Debes seleccionar un aspecto");
                else {
                    Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                    if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                    else responsable.setAspecto(aspecto.get());
                }
            }
        }

        if (isEstudiante) {
            if (estudiante.getCarrera().getId() == null) errores.put("carrera", "Debes seleccionar una carrera");
            else {
                Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                else estudiante.setCarrera(carrera.get());
            }

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

    // 1.2 Consultar usuarios
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerUsuarios(Pageable pageable) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, usuarioRepository.findAllByNoVerificadoIsNull(pageable)), HttpStatus.OK);
    }
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerUsuarios(Pageable pageable, String filtro) {
        return new ResponseEntity<>(new Mensaje(false, "OK", null, usuarioRepository.findByFiltro(filtro, pageable)), HttpStatus.OK);
    }

    // 1.3 Consultar usuario
    @Transactional(readOnly = true)
    public ResponseEntity<Mensaje> obtenerUsuario(long id) {
        Optional<Usuario> resultado = usuarioRepository.findByIdAndActivoIsTrue(id);
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "El usuario no existe", null, null), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new Mensaje(false, "OK", null, resultado.get()), HttpStatus.OK);
    }

    // 1.4 Modificar usuario
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
        else if (!isAdministrador) {
            if (!(administradorRepository.countByUsuario_ActivoIsTrue() > 1)) errores.put("rolAdministrador", "Debe haber al menos un administrador activo en el sistema");
        }
        else if (!isComunidadUtez) errores.put("rolAdministrador", "El usuario debe ser parte de la comunidad UTEZ para ser administrador");

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
                if (responsable.getAspecto().getId() == null) errores.put("aspecto", "Debes seleccionar un aspecto");
                else {
                    Optional<Aspecto> aspecto = aspectoRepository.findById(responsable.getAspecto().getId());
                    if (aspecto.isEmpty()) errores.put("aspecto", "El aspecto seleccionado no existe");
                    else responsable.setAspecto(aspecto.get());
                }
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
                if (estudiante.getCarrera().getId() == null) errores.put("carrera", "Debes seleccionar una carrera");
                else {
                    Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                    if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                    else estudiante.setCarrera(carrera.get());
                }

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

    // 1.5 Eliminar usuario
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> eliminarUsuario(long id) {
        Optional<Usuario> resultado = usuarioRepository.findByIdAndActivoIsTrue(id);
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Usuario inexistente", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultado.get();

        if (usuario.getAdministrador() != null) if (!(administradorRepository.countByUsuario_ActivoIsTrue() > 1)) return new ResponseEntity<>(new Mensaje(true, "Debe haber al menos un administrador activo en el sistema", null, null), HttpStatus.BAD_REQUEST);

        usuario.setActivo(false);
        return new ResponseEntity<>(new Mensaje(false, "Usuario eliminado", null, usuario), HttpStatus.OK);
    }

    // 1.6 Registrarse en el sistema
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> autorregistro(Usuario usuario, Estudiante estudiante) {
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

        if (isEstudiante) {
            if (estudiante.getCarrera().getId() == null) errores.put("carrera", "Debes seleccionar una carrera");
            else {
                Optional<Carrera> carrera = carreraRepository.findById(estudiante.getCarrera().getId());
                if (carrera.isEmpty()) errores.put("carrera", "La carrera seleccionada no existe");
                else estudiante.setCarrera(carrera.get());
            }

            error = Validador.validarCuatrimestreUsuario(estudiante.getCuatrimestre());
            if (error.isPresent()) errores.put("cuatrimestre", error.get());

            error = Validador.validarGrupoUsuario(estudiante.getGrupo());
            if (error.isPresent()) errores.put("grupo", error.get());
        }

        if (errores.size() > 0) return new ResponseEntity<>(new Mensaje(true, "No se pudo registrar al usuario", errores, null), HttpStatus.BAD_REQUEST);

        usuario = usuarioRepository.save(usuario);

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

    // 1.7 Modificar datos personales
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<Mensaje> automodificacion(Usuario usuario, Estudiante estudiante) {
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

        if (isAdministradorActual && !isComunidadUtez) errores.put("rolAdministrador", "Debes ser parte de la comunidad UTEZ para ser administrador");



        if (isResponsableActual && !isComunidadUtez) errores.put("rolResponsable", "Debes ser parte de la comunidad UTEZ para ser responsable de aspecto");


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
}
