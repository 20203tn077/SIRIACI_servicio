package mx.edu.utez.SIRIACI_servicio.model.capsula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Capsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(name ="titulo",unique = true, nullable = false)
    private String titulo;
    @Column(name ="contenido",unique = true, nullable = false)
    private String contenido; //text
    @Column(name ="activo",unique = true, nullable = false)
    private boolean activo;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn( name = "responsable_id", nullable = false, unique = true)
    private Responsable responsable;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "capsula")
    @JsonIgnore
    private List<ImagenCapsula> imagenCapsula;
}
