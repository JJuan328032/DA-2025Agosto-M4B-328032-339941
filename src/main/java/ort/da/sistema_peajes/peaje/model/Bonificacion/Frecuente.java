package ort.da.sistema_peajes.peaje.model.Bonificacion;

public class Frecuente extends Bonificacion{

	public Frecuente() {
		super("Frecuente", 50);
	}

	@Override
	public double calcular(int monto, boolean validar) {
		if(validar) return this.getDescuento().calcularDescuento(monto);
		return 0;
	}
}
