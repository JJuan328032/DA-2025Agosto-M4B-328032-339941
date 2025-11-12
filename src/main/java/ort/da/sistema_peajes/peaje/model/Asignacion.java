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

	public Puesto getPuesto(){ return this.puesto;}
	public Bonificacion getBonificacion(){ return this.bonificacion;}

	public String getPuestoNombre() {
		return this.puesto.getNombre();
	}

	public String getBonificacionNombre() {
		return this.bonificacion.getNombre();
	}

	public LocalDate getFecha() {
		return this.fecha;
	}

	//TODO es la forma correcta de usar equals?
	public boolean equals(Puesto p){
		return this.puesto.equals(p);
	}

	public boolean equals(Asignacion a){
		return this.bonificacion.equals(a.getBonificacion()) && this.puesto.equals(a.getPuesto());
	}

    public double calcularMontoBonificado(int montoTarifa, boolean val) {
        return this.bonificacion.calcular(montoTarifa, val);
    }
}
