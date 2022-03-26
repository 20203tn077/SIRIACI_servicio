package mx.edu.utez.SIRIACI_servicio.model.usuario;

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
import mx.edu.utez.SIRIACI_servicio.model.solicitudRecuperacion.SolicitudRecuperacion;

import javax.persistence.*;
import java.util.List;

@Entity
public class Usuario {
    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    // Atributos
    private long id;
    @Column(nullable = false, length = 64)
    private String nombre;
    @Column(nullable = false, length = 32)
    private String apellido1;
    @Column(length = 32)
    private String apellido2 = "";
    @Column(nullable = false, length = 64)
    private String correo;
    @Column(nullable = false, length = 10)
    private String telefono;
    @Column(nullable = false)
    @JsonIgnore
    private String contrasena;
    @Column(nullable = false)
    private Boolean activo = false;
    @Column(nullable = false)
    private Boolean comunidadUtez;

    // Relaciones de otras tablas con esta
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Administrador admnistrador;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Estudiante estudiante;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Bloqueo bloqueo;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private NoVerificado noVerificado;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private Responsable responsable;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Incidencia> incidencias;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Notificacion> notificaciones;
    @OneToOne(mappedBy = "usuario")
    @JsonIgnore
    private SolicitudRecuperacion solicitudRecuperación;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<DispositivoMovil> dispositivosMoviles;
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private List<Capsula> capsulas;

    public Usuario() {
    }

    public Usuario(String nombre, String apellido1, String apellido2, String correo, String telefono, String contrasena) {
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.correo = correo;
        this.telefono = telefono;
        this.contrasena = contrasena;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
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

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Boolean getComunidadUtez() {
        return comunidadUtez;
    }

    public void setComunidadUtez(Boolean comunidadUtez) {
        this.comunidadUtez = comunidadUtez;
    }

    public Administrador getAdmnistrador() {
        return admnistrador;
    }

    public void setAdmnistrador(Administrador admnistrador) {
        this.admnistrador = admnistrador;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Bloqueo getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(Bloqueo bloqueo) {
        this.bloqueo = bloqueo;
    }

    public NoVerificado getNoVerificado() {
        return noVerificado;
    }

    public void setNoVerificado(NoVerificado noVerificado) {
        this.noVerificado = noVerificado;
    }

    public Responsable getResponsable() {
        return responsable;
    }

    public void setResponsable(Responsable responsable) {
        this.responsable = responsable;
    }

    public List<Incidencia> getIncidencias() {
        return incidencias;
    }

    public void setIncidencias(List<Incidencia> incidencias) {
        this.incidencias = incidencias;
    }

    public List<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public SolicitudRecuperacion getSolicitudRecuperación() {
        return solicitudRecuperación;
    }

    public void setSolicitudRecuperación(SolicitudRecuperacion solicitudRecuperación) {
        this.solicitudRecuperación = solicitudRecuperación;
    }

    public List<DispositivoMovil> getDispositivosMoviles() {
        return dispositivosMoviles;
    }

    public void setDispositivosMoviles(List<DispositivoMovil> dispositivosMoviles) {
        this.dispositivosMoviles = dispositivosMoviles;
    }

    public List<Capsula> getCapsulas() {
        return capsulas;
    }

    public void setCapsulas(List<Capsula> capsulas) {
        this.capsulas = capsulas;
    }
}
