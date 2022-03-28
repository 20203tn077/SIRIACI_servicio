package mx.edu.utez.SIRIACI_servicio.model.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreoAndActivoIsTrue(String correo);
    Optional<Usuario> findByCorreoAndActivoIsTrue(String correo);
    Optional<Usuario> findByIdAndActivoIsTrue(long id);
    Page<Usuario> findAllByNoVerificadoIsNull(Pageable pageable);
    @Query(value = "SELECT U FROM Usuario U LEFT OUTER JOIN NoVerificado N ON U=N.usuario WHERE (N.id IS NULL) AND ((CONCAT(U.nombre, ' ', U.apellido1, ' ', U.apellido2) LIKE %:filtro%) OR (U.correo LIKE %:filtro%))")
    Page<Usuario> findByFiltro(@Param("filtro") String filtro, Pageable pageable);
}
