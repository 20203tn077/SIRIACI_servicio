package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoVerificadoRepository extends JpaRepository<NoVerificado, Long> {
    boolean existsByUsuarioId(long id);
    boolean existsByCodigo(String codigo);
    NoVerificado findByCodigo(String codigo);

}
