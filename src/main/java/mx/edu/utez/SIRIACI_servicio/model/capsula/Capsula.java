package mx.edu.utez.SIRIACI_servicio.model.capsula;

import mx.edu.utez.SIRIACI_servicio.controller.Capsulas.CapsulaSalidaDTO;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Capsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido; //text
    @Column(nullable = false)
    private Boolean activo = true;
    @Column(nullable = false)
    private LocalDateTime fechaPublicacion;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn( name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relaciones de otras tablas con esta
    @OneToMany(mappedBy = "capsula")
    private List<ImagenCapsula> imagenesCapsula;

    public Capsula() {
    }

    public Capsula(Long id, String titulo, String contenido, Usuario usuario) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
        this.usuario = usuario;
    }

    public Capsula(String titulo, String contenido, LocalDateTime fechaPublicacion, Usuario usuario) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaPublicacion = fechaPublicacion;
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

    public CapsulaSalidaDTO convertirSalida() {
        return new CapsulaSalidaDTO(this);
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

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
