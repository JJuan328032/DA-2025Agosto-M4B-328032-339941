package ort.da.sistema_peajes.peaje.dto;

public class PropietarioBonificacionDTO {

    private String nombreCompleto;
    private String estado;

    public PropietarioBonificacionDTO(String nombreCompleto, String estado) {
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
    }

    
    public String getNombreCompleto() {
        return nombreCompleto;
    }       
    
    public String getEstado() {
        return estado;
    }

}
