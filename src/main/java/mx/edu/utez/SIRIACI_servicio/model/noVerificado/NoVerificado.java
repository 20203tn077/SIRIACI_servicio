package mx.edu.utez.SIRIACI_servicio.model.noVerificado;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class NoVerificado {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos
    @Column(nullable = false, unique = true, columnDefinition = "BINARY(16)")
    private UUID codigo;

    // Llaves foraneas
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    public NoVerificado(UUID codigo, Usuario usuario) {
        this.codigo = codigo;
        this.usuario = usuario;
    }

    public NoVerificado() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public void setCodigo(UUID codigo) {
        this.codigo = codigo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
