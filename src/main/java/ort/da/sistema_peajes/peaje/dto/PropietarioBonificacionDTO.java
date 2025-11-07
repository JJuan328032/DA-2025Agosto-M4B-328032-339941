package ort.da.sistema_peajes.peaje.dto;

import java.util.ArrayList;

public class PropietarioBonificacionDTO {

    //info propietario
    private String nombreCompleto;
    private String estado;

    //info bonificacion
    private ArrayList<AsignacionDTO> asignaciones;


    public PropietarioBonificacionDTO(String nombreCompleto, String estado, ArrayList<AsignacionDTO> asignaciones) {
        this.nombreCompleto = nombreCompleto;
        this.estado = estado;
        this.asignaciones = asignaciones;
    }

    
    public String getNombreCompleto() {
        return nombreCompleto;
    }       
    
    public String getEstado() {
        return estado;
    }

    public ArrayList<AsignacionDTO> getAsignaciones(){ return this.asignaciones;}
}
