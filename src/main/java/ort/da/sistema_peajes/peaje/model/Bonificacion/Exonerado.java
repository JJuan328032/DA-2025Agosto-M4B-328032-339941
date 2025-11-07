package ort.da.sistema_peajes.peaje.model.Bonificacion;

public class Exonerado extends Bonificacion{

	public Exonerado() {
		super("Exonerado", 100);
	}

	@Override
	public double calcular(int monto, boolean validar) {
		return this.getDescuento().calcularDescuento(monto);
	}
}
