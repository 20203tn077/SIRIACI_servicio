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
    private Byte id;

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

    public Aspecto() {
    }

    public Aspecto(Byte id) {
        this.id = id;
    }

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public List<Responsable> getResponsables() {
        return responsables;
    }

    public void setResponsables(List<Responsable> responsables) {
        this.responsables = responsables;
    }
}
