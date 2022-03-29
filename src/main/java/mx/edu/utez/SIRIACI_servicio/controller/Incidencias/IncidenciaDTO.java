package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.controller.ImagenDTO;
import mx.edu.utez.SIRIACI_servicio.model.aspecto.Aspecto;
import mx.edu.utez.SIRIACI_servicio.model.estado.Estado;
import mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia.ImagenIncidencia;
import mx.edu.utez.SIRIACI_servicio.model.importancia.Importancia;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

public class IncidenciaDTO {
    private Byte aspecto;
    private String descripcion;
    private Byte importancia;
    private Double longitud;
    private Double latitud;
    private List<ImagenDTO> imagenesIncidencia;

    public Byte getAspecto() {
        return aspecto;
    }

    public void setAspecto(Byte aspecto) {
        this.aspecto = aspecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Byte getImportancia() {
        return importancia;
    }

    public void setImportancia(Byte importancia) {
        this.importancia = importancia;
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

    public List<ImagenDTO> getImagenesIncidencia() {
        return imagenesIncidencia;
    }

    public void setImagenesIncidencia(List<ImagenDTO> imagenesIncidencia) {
        this.imagenesIncidencia = imagenesIncidencia;
    }
}
