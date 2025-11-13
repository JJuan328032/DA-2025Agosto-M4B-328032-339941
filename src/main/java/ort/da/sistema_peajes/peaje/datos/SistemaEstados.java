package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoFactory;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;

public class SistemaEstados {

    private ArrayList<EstadoPropietario> estados;

    public SistemaEstados() {
        this.estados = new ArrayList<>();

        // Inicializamos con los estados básicos del sistema usando el Factory
        inicializarEstadosBase();
    }

    private void inicializarEstadosBase() {
        this.estados.add(EstadoFactory.crear("Habilitado"));
        this.estados.add(EstadoFactory.crear("Deshabilitado"));
        this.estados.add(EstadoFactory.crear("Suspendido"));
        this.estados.add(EstadoFactory.crear("Penalizado"));
    }

    public void agregarEstado(EstadoPropietario estado) {
        this.estados.add(estado);
    } 

    public ArrayList<EstadoPropietario> getEstados() {
        return estados;
    }

    public EstadoPropietario buscarEstadosNombre(String nombre) throws EstadoException {
        for (EstadoPropietario b : this.estados) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {
                return b;
            }
        }

        throw new EstadoException(nombre);
	}

    public EstadoPropietario agregarEstado(String tipo) {
        EstadoPropietario b = EstadoFactory.crear(tipo);
        agregarEstado(b);
        return b;
    }
}
