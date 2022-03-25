package mx.edu.utez.SIRIACI_servicio.model.estudiante;

import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Estudiante {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false)
    private byte cuatrimestre;
    @Column(nullable = false)
    private char grupo;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn( name = "carrera_id", nullable = false)
    private Carrera carrera;

    public Estudiante() {
    }

    public Estudiante(byte cuatrimestre, char grupo, Carrera carrera) {
        this.cuatrimestre = cuatrimestre;
        this.grupo = grupo;
        this.carrera = carrera;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(byte cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public char getGrupo() {
        return grupo;
    }

    public void setGrupo(char grupo) {
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
