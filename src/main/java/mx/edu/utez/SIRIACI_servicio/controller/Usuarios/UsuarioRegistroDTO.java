package mx.edu.utez.SIRIACI_servicio.controller.Usuarios;

public class UsuarioRegistroDTO {
    private String nombre;
    private String apellido1;
    private String apellido2;
    private String correo;
    private String telefono;
    private String contrasena;
    private boolean admnistrador;
    private boolean responsable;
    private byte aspecto;
    private short carrera;
    private byte cuatrimestre;
    private String grupo;

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

    public boolean isAdmnistrador() {
        return admnistrador;
    }

    public void setAdmnistrador(boolean admnistrador) {
        this.admnistrador = admnistrador;
    }

    public boolean isResponsable() {
        return responsable;
    }

    public void setResponsable(boolean responsable) {
        this.responsable = responsable;
    }

    public byte getAspecto() {
        return aspecto;
    }

    public void setAspecto(byte aspecto) {
        this.aspecto = aspecto;
    }

    public short getCarrera() {
        return carrera;
    }

    public void setCarrera(short carrera) {
        this.carrera = carrera;
    }

    public byte getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(byte cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
}
