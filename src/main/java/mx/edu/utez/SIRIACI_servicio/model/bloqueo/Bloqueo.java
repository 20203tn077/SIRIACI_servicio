package mx.edu.utez.SIRIACI_servicio.model.bloqueo;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Bloqueo {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false)
    private Date tiempoBloqueo;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public Bloqueo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTiempoBloqueo() {
        return tiempoBloqueo;
    }

    public void setTiempoBloqueo(Date tiempoBloqueo) {
        this.tiempoBloqueo = tiempoBloqueo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
