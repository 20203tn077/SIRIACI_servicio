package mx.edu.utez.SIRIACI_servicio.model.carrera;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.division.Division;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;

import javax.persistence.*;
import java.util.List;

@Entity
public class Carrera {
    @Id
    private short id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @ManyToOne()
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    @OneToMany(mappedBy = "carrera")
    @JsonIgnore
    private List<Estudiante> estudiantes;
}
