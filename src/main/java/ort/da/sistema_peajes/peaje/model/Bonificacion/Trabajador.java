package ort.da.sistema_peajes.peaje.model.Bonificacion;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class Trabajador extends Bonificacion{

	public Trabajador() {
		super("Trabajador", 80);
	}

	@Override
	public double calcular(int monto, boolean validar) {
		LocalDate fecha = LocalDate.now();
		if(fecha.getDayOfWeek().equals(DayOfWeek.SATURDAY) || fecha.getDayOfWeek().equals(DayOfWeek.SUNDAY)) return monto;
		return this.getDescuento().calcularDescuento(monto);
	}
}
