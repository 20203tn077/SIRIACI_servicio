package mx.edu.utez.SIRIACI_servicio.model.division;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;

import javax.persistence.*;
import java.util.List;

@Entity
public class Division {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;
    @Column(nullable = false, unique = true)
    private String nombre;
    @OneToMany(mappedBy = "division")
    @JsonIgnore
    private List<Carrera> carreras;
}
