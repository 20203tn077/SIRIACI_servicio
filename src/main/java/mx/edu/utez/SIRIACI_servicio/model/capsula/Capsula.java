package mx.edu.utez.SIRIACI_servicio.model.capsula;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.List;

@Entity
public class Capsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(name ="titulo", nullable = false)
    private String titulo;
    @Column(name ="contenido", nullable = false)
    private String contenido; //text
    @Column(name ="activo", nullable = false)
    private Boolean activo = true;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn( name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "capsula")
    @JsonIgnore
    private List<ImagenCapsula> imagenesCapsula;

    public Capsula() {
    }

    public Capsula(Long id, String titulo, String contenido, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
    }

    public Capsula(String titulo, String contenido, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
    }

    public Capsula(Long id, String titulo, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    public void actualizar(Capsula capsula) {
        if (capsula.titulo != null && this.titulo != capsula.titulo) this.titulo = capsula.titulo;
        if (capsula.contenido != null && this.contenido != capsula.contenido) this.contenido = capsula.contenido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Boolean isActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<ImagenCapsula> getImagenesCapsula() {
        return imagenesCapsula;
    }

    public void setImagenesCapsula(List<ImagenCapsula> imagenesCapsula) {
        this.imagenesCapsula = imagenesCapsula;
    }
}
