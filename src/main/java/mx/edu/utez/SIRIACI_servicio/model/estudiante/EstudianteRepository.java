package mx.edu.utez.SIRIACI_servicio.model.estudiante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    void deleteAllByUsuario_CorreoAndUsuario_NoVerificado_CodigoIsNot(String correo, String codigo);
}
