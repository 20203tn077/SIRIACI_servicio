package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion;

import mx.edu.utez.SIRIACI_servicio.model.administrador.AdministradorRepository;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.EstudianteRepository;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificado;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificadoRepository;
import mx.edu.utez.SIRIACI_servicio.model.responsable.ResponsableRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import mx.edu.utez.SIRIACI_servicio.util.Mensaje;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VerificacionService {
    @Autowired
    AdministradorRepository administradorRepository;
    @Autowired
    ResponsableRepository responsableRepository;
    @Autowired
    EstudianteRepository estudianteRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    NoVerificadoRepository noVerificadoRepository;

    // 4.4 Validar código de verificación
    @Transactional
    public ResponseEntity<Mensaje> verificarUsuario(String codigo) {
        if (codigo == null)
            return new ResponseEntity<>(new Mensaje(true, "Código de verificación ausente", null, null), HttpStatus.BAD_REQUEST);

        Optional<NoVerificado> resultado = noVerificadoRepository.findByCodigo(codigo);
        if (resultado.isEmpty()) return new ResponseEntity<>(new Mensaje(true, "Código de verificación inválido", null, null), HttpStatus.BAD_REQUEST);
        Usuario usuario = resultado.get().getUsuario();
        
        administradorRepository.deleteAllByUsuario_CorreoAndUsuario_NoVerificado_CodigoIsNot(usuario.getCorreo(), codigo);
        responsableRepository.deleteAllByUsuario_CorreoAndUsuario_NoVerificado_CodigoIsNot(usuario.getCorreo(), codigo);
        estudianteRepository.deleteAllByUsuario_CorreoAndUsuario_NoVerificado_CodigoIsNot(usuario.getCorreo(), codigo);
        noVerificadoRepository.deleteAllByUsuario_Correo(usuario.getCorreo());
        System.out.println(usuario.getCorreo());
        System.out.println(usuario.getId());
        usuarioRepository.deleteAllByCorreoAndIdIsNot(usuario.getCorreo(), usuario.getId());

        resultado.get().getUsuario().setActivo(true);
        return new ResponseEntity<>(new Mensaje(false, "Usuario verificado", null, null), HttpStatus.OK);
    }

}
