package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Bonificacion.*;

public class SistemaBonificaciones {
    // Implementación del sistema de bonificaciones
    private ArrayList<Bonificacion> bonificaciones;

    public SistemaBonificaciones() {
        this.bonificaciones = new ArrayList<>();
    }

    public void agregarBonificacion(Bonificacion bonificacion) {
        this.bonificaciones.add(bonificacion);
    } 

    public ArrayList<Bonificacion> getBonificaciones() {
        return bonificaciones;
    }

    public <T extends Bonificacion> T buscarBonificacionNombre(String bonificacion, ArrayList<T> lista) throws EstadoException {
    for (T u : lista) {
        if (u.getNombre().equalsIgnoreCase(bonificacion)) {
            return u;
        }
    }
    return null;
	}


    public Bonificacion obtenerBonificacionByNombre(String bonificacion) throws EstadoException {
        return buscarBonificacionNombre(bonificacion, this.bonificaciones);
    }

    public Bonificacion agregarBonificacion(int i, String descripcion) {
        //como sabemos que tipo de bonificacion hay que crear?
        return null;
    }

  

}
    