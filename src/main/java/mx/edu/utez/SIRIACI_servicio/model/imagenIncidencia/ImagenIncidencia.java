package mx.edu.utez.SIRIACI_servicio.model.imagenIncidencia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;

import javax.persistence.*;

@Entity
public class ImagenIncidencia {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, columnDefinition = "blob")
    private byte[] imagen;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "incidencia_id", nullable = false)
    @JsonIgnore
    private Incidencia incidencia;

    public ImagenIncidencia() {
    }

    public ImagenIncidencia(byte[] imagen) {
        this.imagen = imagen;
    }

    public ImagenIncidencia(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Incidencia getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(Incidencia incidencia) {
        this.incidencia = incidencia;
    }
}
