package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.PuestoException;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Tarifa;

public class SistemaPuestos {

	private ArrayList<Puesto> puestos;

	public SistemaPuestos() {
		this.puestos = new ArrayList<>();
	}

	public void agregarPuesto(Puesto puesto) {
		if(!existePuesto(puesto)) this.puestos.add(puesto);
	}

	public ArrayList<Puesto> getPuestos() {
		return this.puestos;
	}

    public Puesto obtenerPuestoPorNombre(String nombre) throws PuestoException{
		for (Puesto p : this.puestos) {
			if (p.getNombre().equals(nombre)) {
				return p;
			}
		}

		throw new PuestoException(nombre);
    }

	private boolean existePuesto(Puesto puesto){
		for(Puesto p : this.puestos) if(p.equals(puesto)) return true;
		return false;
	}

    public ArrayList<Tarifa> obtenerTarifasPorPuestoNombre(String nombre) throws PuestoException{
        return obtenerPuestoPorNombre(nombre).getTarifas();
    }

	/*
    public Tarifa obtenerTarifaSegunPuestoYVehiculo(String puesto, Vehiculo vehiculo) throws Exception {
        return this.obtenerPuestoPorNombre(puesto).obtenerTarifaSegunCategoriaVehiculo(vehiculo);
    }
	*/

    public Puesto obtenerPuestoPorIndice(int indicePuesto) throws PuestoException{
		if(indicePuesto > -1 && indicePuesto < this.puestos.size()) return this.puestos.get(indicePuesto);
		throw new PuestoException("indice inaccesible");
    }
}
