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

    public Carrera() {
    }

    public Carrera(short id) {
        this.id = id;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
