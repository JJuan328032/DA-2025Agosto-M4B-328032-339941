package ort.da.sistema_peajes.peaje.model;

import java.time.LocalDate;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;

public class Asignacion {

	private Puesto puesto;
	private Bonificacion bonificacion;
	private LocalDate fecha;

	public Asignacion(Puesto puesto, Bonificacion bonificacion, LocalDate fecha) {
		this.puesto = puesto;
		this.bonificacion = bonificacion;
		this.fecha = fecha;
	}


	public String getPuestoNombre() {
		return this.puesto.getNombre();
	}

	public String getBonificacionNombre() {
		return this.bonificacion.getNombre();
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	public boolean equals(Puesto p){
		return this.puesto.equals(p);
	}


    public double calcularMontoBonificado(int montoTarifa, boolean val) {
        return this.bonificacion.calcular(montoTarifa, val);
    }
}
