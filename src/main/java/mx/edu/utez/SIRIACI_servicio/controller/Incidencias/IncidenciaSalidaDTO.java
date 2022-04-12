package mx.edu.utez.SIRIACI_servicio.controller.Incidencias;

import mx.edu.utez.SIRIACI_servicio.model.incidencia.Incidencia;
import java.time.LocalDateTime;

public class IncidenciaSalidaDTO {
    private Long id;
    private String descripcion;
    private LocalDateTime tiempoIncidencia;
    private Boolean activo;
    private String importancia;
    private String estado;
    private String aspecto;

    public IncidenciaSalidaDTO(Incidencia incidencia) {
        this.id = incidencia.getId();
        this.descripcion = incidencia.getDescripcion();
        this.tiempoIncidencia = incidencia.getTiempoIncidencia();
        this.activo = incidencia.isActivo();
        this.importancia = incidencia.getImportancia().getNombre();
        this.estado = incidencia.getEstado().getNombre();
        this.aspecto = incidencia.getAspecto().getNombre();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getTiempoIncidencia() {
        return tiempoIncidencia;
    }

    public void setTiempoIncidencia(LocalDateTime tiempoIncidencia) {
        this.tiempoIncidencia = tiempoIncidencia;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAspecto() {
        return aspecto;
    }

    public void setAspecto(String aspecto) {
        this.aspecto = aspecto;
    }
}
