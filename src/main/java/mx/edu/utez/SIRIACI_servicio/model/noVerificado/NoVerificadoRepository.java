package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoVerificadoRepository extends JpaRepository<NoVerificado, Long> {
    public boolean existsByUsuario(Usuario usuario);

}
