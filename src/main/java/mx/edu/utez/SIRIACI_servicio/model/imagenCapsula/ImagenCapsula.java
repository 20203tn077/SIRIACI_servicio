package mx.edu.utez.SIRIACI_servicio.model.imagenCapsula;

import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;

import javax.persistence.*;
import java.util.Base64;

@Entity
public class ImagenCapsula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, columnDefinition = "blob")
    private byte[] imagen;
    @ManyToOne
    @JoinColumn(name = "capsula_id", nullable = false)
    private Capsula capsula;
}
