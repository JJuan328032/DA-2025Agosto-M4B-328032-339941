package ort.da.sistema_peajes.peaje.model.Bonificacion;

public class Descuento {

	private final double porcentaje;

	public Descuento(double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public double getPorcentaje() {
		return porcentaje;
	}

	public double calcularDescuento(int monto) {
		return (int) (monto * (porcentaje / 100));
	}
}
