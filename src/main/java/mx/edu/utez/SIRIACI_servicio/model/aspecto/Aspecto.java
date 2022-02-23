package mx.edu.utez.SIRIACI_servicio.model.aspecto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;

import javax.persistence.*;
import java.util.List;

@Entity
public class Aspecto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;
    @Column(name = "nombre", length = 32, nullable = false)
    private String nombre;

    @OneToMany(mappedBy = "aspecto")
    @JsonIgnore
    private List<Incidencia> insidencias;

    @OneToMany(mappedBy = "aspecto")
    @JsonIgnore
    private List<Responsable> responsables;
}
