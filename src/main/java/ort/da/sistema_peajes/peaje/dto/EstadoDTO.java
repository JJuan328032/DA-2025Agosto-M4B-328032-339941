package ort.da.sistema_peajes.peaje.dto;

public class EstadoDTO {

    private String nombre;

    public EstadoDTO() {}

    public EstadoDTO(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
