package mx.edu.utez.SIRIACI_servicio.util;

import java.util.List;
import java.util.Map;

public class Mensaje {
    private boolean error;
    private String mensajeGeneral;
    private Map<String, String> errores;
    private Object datos;

    public Mensaje() {}

    public Mensaje(boolean error, String mensajeGeneral, Map errores, Object datos) {
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

    public Map<String, String> getErrores() {
        return errores;
    }

    public void setErrores(Map<String, String> errores) {
        this.errores = errores;
    }

    public Object getDatos() {
        return datos;
    }

    public void setDatos(Object datos) {
        this.datos = datos;
    }
}
