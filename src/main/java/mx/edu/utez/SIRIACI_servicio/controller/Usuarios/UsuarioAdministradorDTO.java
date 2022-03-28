package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

public class UsuarioAdministradorDTO {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String telefono;
    private String contrasena;
    private Boolean administrador;
    private Boolean responsable;
    private Byte aspecto;
    private Short carrera;
    private Byte cuatrimestre;
    private Character grupo;

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

    public Boolean isAdministrador() {
        return administrador;
    }

    public void setAdministrador(Boolean administrador) {
        this.administrador = administrador;
    }

    public Boolean isResponsable() {
        return responsable;
    }

    public void setResponsable(Boolean responsable) {
        this.responsable = responsable;
    }

    public Byte getAspecto() {
        return aspecto;
    }

    public void setAspecto(Byte aspecto) {
        this.aspecto = aspecto;
    }

    public Short getCarrera() {
        return carrera;
    }

    public void setCarrera(Short carrera) {
        this.carrera = carrera;
    }

    public Byte getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(Byte cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Character getGrupo() {
        return grupo;
    }

    public void setGrupo(Character grupo) {
        this.grupo = grupo;
    }
}
