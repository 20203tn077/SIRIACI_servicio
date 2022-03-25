package mx.edu.utez.SIRIACI_servicio.model.capsula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class Capsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(name ="titulo", nullable = false)
    private String titulo;
    @Column(name ="contenido", nullable = false)
    private String contenido; //text
    @Column(name ="activo", nullable = false)
    private boolean activo;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn( name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "capsula")
    @JsonIgnore
    private List<ImagenCapsula> imagenesCapsula;

    public Capsula() {
    }
}
