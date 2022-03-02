package mx.edu.utez.SIRIACI_servicio.model.aspecto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Aspecto {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;

    // Atributos
    @Column(length = 32, nullable = false, unique = true)
    private String nombre;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "aspecto")
    @JsonIgnore
    private List<Incidencia> incidencias;
    @OneToMany(mappedBy = "aspecto")
    @JsonIgnore
    private List<Responsable> responsables;
}
