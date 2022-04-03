package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mx.edu.utez.SIRIACI_servicio.model.administrador.Administrador;
import mx.edu.utez.SIRIACI_servicio.model.bloqueo.Bloqueo;
import mx.edu.utez.SIRIACI_servicio.model.capsula.Capsula;
import mx.edu.utez.SIRIACI_servicio.model.dispositivoMovil.DispositivoMovil;
import mx.edu.utez.SIRIACI_servicio.model.estudiante.Estudiante;
import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import mx.edu.utez.SIRIACI_servicio.model.noVerificado.NoVerificado;
import mx.edu.utez.SIRIACI_servicio.model.notificacion.Notificacion;
import mx.edu.utez.SIRIACI_servicio.model.responsable.Responsable;
import mx.edu.utez.SIRIACI_servicio.model.solicitudRestablecimiento.SolicitudRestablecimiento;
import mx.edu.utez.SIRIACI_servicio.model.usuario.Usuario;
import org.springframework.context.annotation.Bean;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

public class UsuarioSalidaDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String telefono;
    private Boolean activo;

    public UsuarioSalidaDTO(Usuario usuario) {
        this.id = id;
        this.nombre = usuario.getNombre() + " " + usuario.getApellido1() + (usuario.getApellido2() != null ? (" " + usuario.getApellido2()) : "");
        this.correo = usuario.getCorreo();
        this.telefono = usuario.getTelefono();
        this.activo = usuario.isActivo();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
