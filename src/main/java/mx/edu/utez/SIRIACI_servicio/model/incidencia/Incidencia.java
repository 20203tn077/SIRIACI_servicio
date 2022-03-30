package mx.edu.utez.SIRIACI_servicio.model.incidencia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Incidencia {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private Date tiempoIncidencia;
    @Column(nullable = false)
    private Double longitud;
    @Column(nullable = false)
    private Double latitud;
    @Column(nullable = false)
    private Boolean activo = true;
    @Column(length = 128)
    private String comentario;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "importancia_id",  nullable = false)
    private Importancia importancia;
    @ManyToOne
    @JoinColumn(name = "estado_id",  nullable = false)
    private Estado estado;
    @ManyToOne
    @JoinColumn(name = "aspecto_id",  nullable = false)
    private Aspecto aspecto;
    @ManyToOne
    @JoinColumn(name = "usuario_id",  nullable = false)
    private Usuario usuario;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "incidencia")
    private List<ImagenIncidencia> imagenesIncidencia;

    public Incidencia() {
    }

    public Incidencia(String descripcion, Date tiempoIncidencia, Double longitud, Double latitud, Importancia importancia, Aspecto aspecto, Usuario usuario) {
        this.descripcion = descripcion;
        this.tiempoIncidencia = tiempoIncidencia;
        this.longitud = longitud;
        this.latitud = latitud;
        this.importancia = importancia;
        this.aspecto = aspecto;
        this.usuario = usuario;
    }

    public Incidencia(Long id, String descripcion, Double longitud, Double latitud, Importancia importancia, Aspecto aspecto, Usuario usuario) {
        this.id = id;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
        this.importancia = importancia;
        this.aspecto = aspecto;
        this.usuario = usuario;
    }

    public Incidencia(Long id, String comentario) {
        this.id = id;
        this.comentario = comentario;
    }

    public Incidencia(Long id, String comentario, Usuario usuario) {
        this.id = id;
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void actualizar(Incidencia incidencia) {
        if (incidencia.descripcion != null && incidencia.descripcion != this.descripcion) this.descripcion = incidencia.descripcion;
        if (incidencia.longitud != null && incidencia.longitud != this.longitud) this.longitud = incidencia.longitud;
        if (incidencia.latitud != null && incidencia.latitud != this.latitud) this.latitud = incidencia.latitud;
        if (incidencia.importancia != null && incidencia.importancia.getId() != null && incidencia.importancia.getId() != this.importancia.getId()) this.importancia = incidencia.importancia;
        if (incidencia.aspecto != null && incidencia.aspecto.getId() != null && incidencia.aspecto.getId() != this.aspecto.getId()) this.aspecto = incidencia.aspecto;
    }

    public void atender(Incidencia incidencia) {
        if (incidencia.comentario != null && this.comentario != incidencia.comentario) this.comentario = incidencia.comentario;
        if (incidencia.estado.getId() != null && this.estado.getId() != incidencia.estado.getId()) this.estado = incidencia.estado;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getTiempoIncidencia() {
        return tiempoIncidencia;
    }

    public void setTiempoIncidencia(Date tiempoIncidencia) {
        this.tiempoIncidencia = tiempoIncidencia;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Importancia getImportancia() {
        return importancia;
    }

    public void setImportancia(Importancia importancia) {
        this.importancia = importancia;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Aspecto getAspecto() {
        return aspecto;
    }

    public void setAspecto(Aspecto aspecto) {
        this.aspecto = aspecto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ImagenIncidencia> getImagenesIncidencia() {
        return imagenesIncidencia;
    }

    public void setImagenesIncidencia(List<ImagenIncidencia> imagenesIncidencia) {
        this.imagenesIncidencia = imagenesIncidencia;
    }
}
