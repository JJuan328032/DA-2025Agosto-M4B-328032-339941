package ort.da.sistema_peajes.peaje.dto;

public class TarifaDTO {
    private String categoria;
    private int monto;
    

    public TarifaDTO(String cate, int monto){
        this.categoria = cate;
        this.monto = monto;
    }

    public int getMonto(){return this.monto;}
    public String getCategoria(){return this.categoria;}
}
