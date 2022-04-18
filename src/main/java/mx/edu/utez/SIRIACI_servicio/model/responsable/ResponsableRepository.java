package mx.edu.utez.SIRIACI_servicio.model.responsable;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ResponsableRepository extends JpaRepository<Responsable, Long> {
    void deleteAllByUsuario_CorreoAndUsuario_NoVerificado_CodigoIsNot(String correo, UUID codigo);
    List<Responsable> findAllByAspecto_IdAndUsuario_ActivoIsTrue(Byte id);
}
