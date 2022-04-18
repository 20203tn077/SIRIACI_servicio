package mx.edu.utez.SIRIACI_servicio.model.dispositivoMovil;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DispositivoMovil {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String token;
    @Column(nullable = false)
    private LocalDateTime tiempoSuscripcion;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public DispositivoMovil() {
    }

    public DispositivoMovil(String token, LocalDateTime tiempoSuscripcion, Usuario usuario) {
        this.token = token;
        this.tiempoSuscripcion = tiempoSuscripcion;
        this.usuario = usuario;
    }

    public void reemplazar(DispositivoMovil dispositivoMovil) {
        this.token = dispositivoMovil.token;
        this.tiempoSuscripcion = dispositivoMovil.tiempoSuscripcion;
        this.usuario = dispositivoMovil.usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getTiempoSuscripcion() {
        return tiempoSuscripcion;
    }

    public void setTiempoSuscripcion(LocalDateTime tiempoSuscripcion) {
        this.tiempoSuscripcion = tiempoSuscripcion;
    }
}
