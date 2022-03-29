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
    private Long id;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id",nullable = false, unique = true)
    @JsonIgnore
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "aspecto_id", nullable = false)
    private Aspecto aspecto;

    public Responsable() {
    }

    public void actualizar(Responsable responsable) {
        if (this.aspecto.getId() != responsable.aspecto.getId() && (responsable.aspecto != null && responsable.aspecto.getId() != null)) this.aspecto = responsable.aspecto;
    }

    public Responsable(Aspecto aspecto) {
        this.aspecto = aspecto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
