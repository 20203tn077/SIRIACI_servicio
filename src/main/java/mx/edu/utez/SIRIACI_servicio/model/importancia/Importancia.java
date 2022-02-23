package mx.edu.utez.SIRIACI_servicio.model.importancia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;

import javax.persistence.*;
import java.util.List;

@Entity
public class Importancia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 16, nullable = false, unique = true)
    private String nombre;

    @OneToMany(mappedBy = "importancia")
    @JsonIgnore
    private List<Incidencia> insidencias;
}
