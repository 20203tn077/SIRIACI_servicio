package mx.edu.utez.SIRIACI_servicio.util;

import java.util.List;

public class Mensaje {
    private boolean error;
    private String mensajeGeneral;
    private List<ErrorFormulario> errores;
    private Object datos;

    public Mensaje(boolean error, String mensajeGeneral, List<ErrorFormulario> errores, Object datos) {
        this.error = error;
        this.mensajeGeneral = mensajeGeneral;
        this.errores = errores;
        this.datos = datos;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMensajeGeneral() {
        return mensajeGeneral;
    }

    public void setMensajeGeneral(String mensajeGeneral) {
        this.mensajeGeneral = mensajeGeneral;
    }

    public List<ErrorFormulario> getErrores() {
        return errores;
    }

    public void setErrores(List<ErrorFormulario> errores) {
        this.errores = errores;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}
