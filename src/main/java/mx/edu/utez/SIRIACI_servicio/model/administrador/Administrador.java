package mx.edu.utez.SIRIACI_servicio.model.administrador;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Administrador {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    @JsonIgnore
    private Usuario usuario;

    public Administrador() {
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
}
