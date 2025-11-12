package ort.da.sistema_peajes.peaje.dto;

public class CambioEstadoDTO {
    private String cedula;
    private String nuevoEstado;

    public CambioEstadoDTO() {}

    public CambioEstadoDTO(String cedula, String nuevoEstado) {
        this.cedula = cedula;
        this.nuevoEstado = nuevoEstado;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNuevoEstado() {
        return nuevoEstado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
    }
}
