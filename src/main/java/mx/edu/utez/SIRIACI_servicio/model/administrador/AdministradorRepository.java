package mx.edu.utez.SIRIACI_servicio.model.administrador;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    int countByUsuario_ActivoIsTrue();
    void deleteAllByUsuario_CorreoAndUsuario_NoVerificado_CodigoIsNot(String correo, UUID codigo);
}
