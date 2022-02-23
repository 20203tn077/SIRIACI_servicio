package mx.edu.utez.SIRIACI_servicio.model.estudiante;

import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;

@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String carrera;
    @Column(nullable = false)
    private byte cuatrimestre;
    @Column(nullable = false)
    private char grupo;
    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;
}
