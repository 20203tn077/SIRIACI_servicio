package mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SolicitudRestablecimiento {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private LocalDateTime tiempo_solicitud;
    private LocalDateTime tiempo_canjeado;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public SolicitudRestablecimiento() {
    }

    public SolicitudRestablecimiento(String codigo, Usuario usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
    }

    public SolicitudRestablecimiento(String codigo, LocalDateTime tiempo_solicitud, Usuario usuario) {
        this.codigo = codigo;
        this.tiempo_solicitud = tiempo_solicitud;
        this.usuario = usuario;
    }

    public void reemplazar (SolicitudRestablecimiento solicitudRestablecimiento) {
        this.codigo = solicitudRestablecimiento.codigo;
        this.tiempo_solicitud = solicitudRestablecimiento.tiempo_solicitud;
        this.tiempo_canjeado = solicitudRestablecimiento.tiempo_canjeado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getTiempo_solicitud() {
        return tiempo_solicitud;
    }

    public void setTiempo_solicitud(LocalDateTime tiempo_solicitud) {
        this.tiempo_solicitud = tiempo_solicitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getTiempo_canjeado() {
        return tiempo_canjeado;
    }

    public void setTiempo_canjeado(LocalDateTime tiempo_canjeado) {
        this.tiempo_canjeado = tiempo_canjeado;
    }
}
