package mx.edu.utez.SIRIACI_servicio.controller.AccesoYValidacion.Bloqueos;

import mx.edu.utez.SIRIACI_servicio.model.bloqueo.Bloqueo;
import mx.edu.utez.SIRIACI_servicio.model.bloqueo.BloqueoRepository;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class BloqueosService {
    @Autowired
    BloqueoRepository repository;

    // 4.2 Bloquear acceso al sistema por exceso de intentos fallidos
    @Transactional
    public void bloquear(Usuario usuario) {
        repository.save(new Bloqueo(LocalDateTime.now(), usuario));
    }

    // 4.3 Desbloquear cuenta
    @Transactional
    public void desbloquear(Usuario usuario) {
        Optional<Bloqueo> bloqueo = repository.findByUsuario_Id(usuario.getId());
        if (bloqueo.isPresent()) repository.delete(bloqueo.get());
    }

}
