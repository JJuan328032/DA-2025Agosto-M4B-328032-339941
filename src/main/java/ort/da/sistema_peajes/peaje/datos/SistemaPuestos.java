package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;

public class SistemaPuestos {

	private ArrayList<Puesto> puestos;

	public SistemaPuestos() {
		this.puestos = new ArrayList<>();
	}

	public void agregarPuesto(Puesto puesto) {
		this.puestos.add(puesto);
	}

	public ArrayList<Puesto> getPuestos() {
		return this.puestos;
	}

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
}
