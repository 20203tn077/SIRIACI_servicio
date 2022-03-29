package mx.edu.utez.SIRIACI_servicio.model.estado;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Byte> {
    Estado findFirstByOrderById();
}
