package mx.edu.utez.SIRIACI_servicio.controller.Capsulas;

import mx.edu.utez.SIRIACI_servicio.controller.ImagenDTO;

import java.util.List;

public class CapsulaDTO {
    String titulo;
    String contenido;
    List<ImagenDTO> imagenesCapsula;

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

    public List<ImagenDTO> getImagenesCapsula() {
        return imagenesCapsula;
    }

    public void setImagenesCapsula(List<ImagenDTO> imagenesCapsula) {
        this.imagenesCapsula = imagenesCapsula;
    }
}
