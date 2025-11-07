package ort.da.sistema_peajes.peaje.model.Bonificacion;


public abstract class Bonificacion {

    private String nombre;
	private Descuento descuento;

	public Bonificacion(String nombre, double descuento) {
		this.nombre = nombre;
		this.descuento = new Descuento(descuento);
	}

	public String getNombre() {
		return nombre;
	}	
	public Descuento getDescuento() {
        return descuento;
    }
	
	public abstract double calcular(int monto, boolean validar);
	
}
