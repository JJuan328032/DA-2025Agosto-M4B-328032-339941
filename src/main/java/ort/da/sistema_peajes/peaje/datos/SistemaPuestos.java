package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.PuestoException;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Tarifa;
import ort.da.sistema_peajes.peaje.model.Vehiculo;

public class SistemaPuestos {

	private ArrayList<Puesto> puestos;

	public SistemaPuestos() {
		this.puestos = new ArrayList<>();
	}

	public void agregarPuesto(Puesto puesto) throws PuestoException {

		try{
			this.obtenerPuestoPorNombre(puesto.getNombre());
		}catch(PuestoException e){
			this.puestos.add(puesto);
		}

		/*
		if(this.obtenerPuestoPorNombre(puesto.getNombre()) == null) this.puestos.add(puesto);
		else throw new PuestoException("Ya existe el Puesto con nombre: " + puesto.getNombre());
		*/
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

    public ArrayList<Tarifa> obtenerTarifasPorPuestoNombre(String nombre) throws PuestoException{
        return obtenerPuestoPorNombre(nombre).getTarifas();
    }

    public Tarifa obtenerTarifaSegunPuestoYVehiculo(String puesto, Vehiculo vehiculo) throws Exception {
        return this.obtenerPuestoPorNombre(puesto).obtenerTarifaSegunCategoriaVehiculo(vehiculo);
    }

    public Puesto obtenerPuestoPorIndice(int indicePuesto) {
        return this.puestos.get(indicePuesto);
    }

	/*
	public <T extends Puesto> T buscarPuestoNombre(String puesto, ArrayList<T> lista) throws EstadoException {
    for (T u : lista) {
        if (u.getNombre().equalsIgnoreCase(puesto)) {
            return u;
        }
    }
    return null;
	}
	

	public Puesto obtenerPuestoByNombre(String puesto) throws EstadoException {
		return buscarPuestoNombre(puesto, this.puestos);
	}
		*/
}
