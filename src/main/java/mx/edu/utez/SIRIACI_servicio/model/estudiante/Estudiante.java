package mx.edu.utez.SIRIACI_servicio.model.estudiante;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Estudiante {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false)
    private Byte cuatrimestre;
    @Column(nullable = false)
    private Character grupo;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @JsonIgnore
    private Usuario usuario;
    @ManyToOne
    @JoinColumn( name = "carrera_id", nullable = false)
    private Carrera carrera;

    public Estudiante() {
    }

    public Estudiante(Byte cuatrimestre, Character grupo, Carrera carrera) {
        this.cuatrimestre = cuatrimestre;
        this.grupo = grupo;
        this.carrera = carrera;
    }

    public void actualizar(Estudiante estudiante) {
        if (this.cuatrimestre != estudiante.cuatrimestre && estudiante.cuatrimestre != null) this.cuatrimestre = estudiante.cuatrimestre;
        if (this.grupo != estudiante.grupo && estudiante.grupo != null) this.grupo = estudiante.grupo;
        if (this.carrera != estudiante.carrera && estudiante.carrera != null && estudiante.carrera.getId() != null) this.carrera = estudiante.carrera;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(Byte cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Character getGrupo() {
        return grupo;
    }

    public void setGrupo(Character grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }
}
