package mx.edu.utez.SIRIACI_servicio.security;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import mx.edu.utez.SIRIACI_servicio.model.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    IntentoInicioSesionService intentoInicioSesionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        intentoInicioSesionService.verificarExistencia(username);
        intentoInicioSesionService.verificarBloqueo(username);
        return DetalleUsuario.build(usuarioRepository.findByCorreoAndActivoIsTrue(username).get());
    }
}
