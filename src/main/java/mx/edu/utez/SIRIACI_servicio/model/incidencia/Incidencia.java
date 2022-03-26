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
    private Boolean activo;
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
    @JsonIgnore
    private List<ImagenIncidencia> imagenesIncidencia;

    public Incidencia() {
    }

    public Long getId() {
        return id;
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
