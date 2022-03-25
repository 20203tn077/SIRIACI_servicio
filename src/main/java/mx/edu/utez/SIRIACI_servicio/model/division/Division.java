package mx.edu.utez.SIRIACI_servicio.model.division;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;

import javax.persistence.*;
import java.util.List;

@Entity
public class Division {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private byte id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String nombre;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "division")
    //@JsonIgnore
    private List<Carrera> carreras;

    public Division() {
    }

    public byte getId() {
        return id;
    }

    public void setId(byte id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }
}
