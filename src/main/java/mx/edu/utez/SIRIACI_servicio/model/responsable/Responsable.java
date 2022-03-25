package mx.edu.utez.SIRIACI_servicio.model.responsable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class Responsable {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id",nullable = false, unique = true)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "aspecto_id", nullable = false)
    private Aspecto aspecto;

    public Responsable() {
    }

    public Responsable(Aspecto aspecto) {
        this.aspecto = aspecto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Aspecto getAspecto() {
        return aspecto;
    }

    public void setAspecto(Aspecto aspecto) {
        this.aspecto = aspecto;
    }
}
