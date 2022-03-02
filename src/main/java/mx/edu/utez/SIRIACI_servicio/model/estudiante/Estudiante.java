package mx.edu.utez.SIRIACI_servicio.model.estudiante;

import mx.edu.utez.SIRIACI_servicio.model.carrera.Carrera;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Estudiante {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos
    @Column(nullable = false)
    private byte cuatrimestre;
    @Column(nullable = false)
    private char grupo;

    // Llaves foraneas
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    // Relaciones de otras tablas con esta
    @ManyToOne
    @JoinColumn( name = "carrera_id", nullable = false)
    private Carrera carrera;
}
