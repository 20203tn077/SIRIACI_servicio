package mx.edu.utez.SIRIACI_servicio.controller.Capsulas;

import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.imagenCapsula.ImagenCapsula;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

public class CapsulaSalidaDTO {
    private Long id;
    private String titulo;
    private Boolean activo;
    private LocalDateTime fechaPublicacion;
    private String contenido;
    private byte[] imagenCapsula;

    public CapsulaSalidaDTO(Capsula capsula) {
        this.id = capsula.getId();
        this.titulo = capsula.getTitulo();
        this.activo = capsula.isActivo();
        this.fechaPublicacion = capsula.getFechaPublicacion();
        this.contenido = capsula.getContenido().length() > 64 ? capsula.getContenido().substring(0, 64).trim() + "..." : capsula.getContenido();
        this.imagenCapsula = capsula.getImagenesCapsula().isEmpty() ? null : capsula.getImagenesCapsula().get(0).getImagen();
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public byte[] getImagenCapsula() {
        return imagenCapsula;
    }

    public void setImagenCapsula(byte[] imagenCapsula) {
        this.imagenCapsula = imagenCapsula;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
}
