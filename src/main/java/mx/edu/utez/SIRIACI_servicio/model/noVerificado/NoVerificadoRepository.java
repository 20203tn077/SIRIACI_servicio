package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoVerificadoRepository extends JpaRepository<NoVerificado, Long> {
    Optional<NoVerificado> findByCorreoAndCodigo(String correo, UUID codigo);
    void deleteAllByUsuario_Correo(String correo);
}
