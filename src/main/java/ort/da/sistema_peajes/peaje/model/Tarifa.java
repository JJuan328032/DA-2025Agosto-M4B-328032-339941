package ort.da.sistema_peajes.peaje.model;

public class Tarifa {
    
    private String tipo;
    private int monto;
    private CategoriaVehiculos categoria;

    public Tarifa(String tipo, int monto, String nombreCategoria) {
        this.tipo = tipo;
        this.monto = monto;
        this.categoria = new CategoriaVehiculos(nombreCategoria);
    }

    public Tarifa(String tipo, int monto, CategoriaVehiculos categoria){
        this.tipo = tipo;
        this.monto = monto;
        this.categoria = categoria;
    }

    public int getMonto() {
        return monto;
    }

    public CategoriaVehiculos getCategoria() {
        return categoria;
    }

    public String getTipo() {
        return this.tipo;
    }

    public String getCategoriaNombre(){ return this.categoria.getNombre();}

    public String toString(){
        return "Tipo: " + this.tipo + " Monto: " + this.monto + " Categoria: " + this.getCategoriaNombre();
    }

    public boolean mismaCategoria(CategoriaVehiculos categoria2) {
        return this.categoria.equals(categoria2);
    }
}
