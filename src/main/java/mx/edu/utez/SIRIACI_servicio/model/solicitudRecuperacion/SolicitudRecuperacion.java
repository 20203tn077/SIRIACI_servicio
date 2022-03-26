package mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;

@Entity
public class SolicitudRecuperacion {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, unique = true)
    private String codigo;
    @Column(nullable = false)
    private Date tiempo_solicitud;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public SolicitudRecuperacion() {
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

    public Date getTiempo_solicitud() {
        return tiempo_solicitud;
    }

    public void setTiempo_solicitud(Date tiempo_solicitud) {
        this.tiempo_solicitud = tiempo_solicitud;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
