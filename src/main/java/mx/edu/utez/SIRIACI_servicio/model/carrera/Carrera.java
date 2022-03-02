package mx.edu.utez.SIRIACI_servicio.model.carrera;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.division.Division;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;

import javax.persistence.*;
import java.util.List;

@Entity
public class Carrera {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String nombre;

    // Llaves foraneas
    @ManyToOne()
    @JoinColumn(name = "division_id", nullable = false)
    private Division division;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "carrera")
    @JsonIgnore
    private List<Estudiante> estudiantes;
}
