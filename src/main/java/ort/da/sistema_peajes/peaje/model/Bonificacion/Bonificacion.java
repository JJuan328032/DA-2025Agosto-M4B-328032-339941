package ort.da.sistema_peajes.peaje.model.Bonificacion;


public abstract class Bonificacion {

    private String nombre;
	private Descuento descuento;

	public Bonificacion(String nombre, double descuento) {
		this.nombre = nombre;
		this.descuento = new Descuento(descuento);
	}

	public Bonificacion(String nombre){
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}	
	public Descuento getDescuento() {
        return descuento;
    }
	
	public abstract double calcular(int monto, boolean validar);

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Bonificacion other = (Bonificacion) obj;
		return nombre.equals(other.nombre) && descuento.equals(other.descuento);
	}
	
}
