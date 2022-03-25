package mx.edu.utez.SIRIACI_servicio.model.importancia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportanciaRepository extends JpaRepository<Importancia, Byte> {
}
