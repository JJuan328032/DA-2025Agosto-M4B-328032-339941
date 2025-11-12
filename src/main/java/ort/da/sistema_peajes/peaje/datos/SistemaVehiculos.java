package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.VehiculoException;
import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class SistemaVehiculos {

	private ArrayList<Vehiculo> vehiculos;

	public SistemaVehiculos() {
		this.vehiculos = new ArrayList<>();
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void agregarVehiculo(Vehiculo vehiculo) {
		this.vehiculos.add(vehiculo);
	}

	/*
	public void agregarVariosVehiculos(ArrayList<Vehiculo> vehiculos) {
		this.vehiculos.addAll(vehiculos);
	}
	*/

	public Vehiculo obtenerVehiculoPorMatricula(String matricula) throws VehiculoException{
		for (Vehiculo vehiculo : vehiculos) if (vehiculo.igualPatente(matricula)) return vehiculo;
		throw new VehiculoException(matricula);
	}

	/*
    public void asociarVehiculoAPropietario(Vehiculo v, Propietario p) {
        v.setPropietario(p);
    }
	*/

}
