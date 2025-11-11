package ort.da.sistema_peajes.peaje.model;

import java.util.ArrayList;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;

public class Puesto {
	
	private String nombre;
	private String direccion;
	private ArrayList<Tarifa> tarifas;
	private ArrayList<Bonificacion> bonificaciones;

	public Puesto(String nombre, String direccion) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.tarifas = new ArrayList<>();
		this.bonificaciones = new ArrayList<>();
	}

	public String getNombre() {
		return nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void agregarTarifa(Tarifa tarifa) {
		this.tarifas.add(tarifa);
	}

	public ArrayList<Tarifa> getTarifas() {
		return tarifas;
	}

	public void agregarBonificacion(Bonificacion bonificacion) {
		this.bonificaciones.add(bonificacion);
	}

	public ArrayList<Bonificacion> getBonificaciones() {
		return bonificaciones;
	}

    public void setTarifas(ArrayList<Tarifa> nuevasTarifas) {
		for(Tarifa t : nuevasTarifas) this.tarifas.add(t);
    }

	public int obtenerMontoTarifaSegunVehiculo(Vehiculo vehiculo) throws Exception{
		return this.obtenerTarifaSegunCategoriaVehiculo(vehiculo).getMonto();
	}

	public Tarifa obtenerTarifaSegunCategoriaVehiculo(Vehiculo vehiculo) throws Exception {

		for (Tarifa tarifa : this.tarifas) {
			if (tarifa.mismaCategoria(vehiculo.getCategoria())) {
				return tarifa;
			}
		}

		//TODO: generalizar el mensaje en controller
		throw new Exception("Hubo un error inesperado. No se encuentra la Tarifa");
	}

	public boolean equals(Puesto p){
		return this.getNombre().equals(p.getNombre());
	}

	public String toString(){
		return "Nombre: " + nombre + " Direccion: " + direccion;
	}
}
