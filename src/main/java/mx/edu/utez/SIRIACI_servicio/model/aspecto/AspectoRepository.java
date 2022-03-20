package mx.edu.utez.SIRIACI_servicio.model.aspecto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AspectoRepository extends JpaRepository<Aspecto, Byte> {
    Aspecto findById(long id);
}
