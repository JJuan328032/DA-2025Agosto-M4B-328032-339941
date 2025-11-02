package ort.da.sistema_peajes.peaje.model;

public class CategoriaVehiculos {
    private String nombre;

    public CategoriaVehiculos(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaVehiculos that = (CategoriaVehiculos) o;
        if (this.nombre == null || that.getNombre() == null) return false;
        return this.nombre.trim().equalsIgnoreCase(that.getNombre().trim());
    }


    @Override
    public int hashCode() {
        return nombre != null ? nombre.hashCode() : 0;
    }

}
