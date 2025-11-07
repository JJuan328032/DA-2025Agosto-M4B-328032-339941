package ort.da.sistema_peajes.peaje.dto;

public class NombreDTO {
    private String nombre;

    public NombreDTO(String nombre){
        this.nombre = nombre;
    }

    public String getNombre(){ return this.nombre;}
}
