package mx.edu.utez.SIRIACI_servicio.model.estado;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;

import javax.persistence.*;
import java.util.List;

@Entity
public class Estado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;
    @Column(name = "nombre", length = 16, nullable = false)
    private String nombre;
    @OneToMany(mappedBy = "estado")
    @JsonIgnore
    private List<Incidencia> insidencias;
}
