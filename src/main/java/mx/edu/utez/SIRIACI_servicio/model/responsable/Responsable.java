package mx.edu.utez.SIRIACI_servicio.model.responsable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class Responsable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    @JoinColumn(name = "usuario_id",nullable = false, unique = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "aspecto_id", nullable = false)
    private Aspecto aspecto;

    @OneToMany(mappedBy = "responsable")
    @JsonIgnore
    private List<Capsula> capsulas;
}
