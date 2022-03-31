package mx.edu.utez.SIRIACI_servicio.model.imagenCapsula;

import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class ImagenCapsula {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, columnDefinition = "blob")
    private byte[] imagen;

    // Llaves foraneas
    @ManyToOne
    @JoinColumn(name = "capsula_id", nullable = false)
    private Capsula capsula;

    public ImagenCapsula() {
    }

    public ImagenCapsula(byte[] imagen) {
        this.imagen = imagen;
    }

    public ImagenCapsula(Long id) {
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

    public Capsula getCapsula() {
        return capsula;
    }

    public void setCapsula(Capsula capsula) {
        this.capsula = capsula;
    }
}
