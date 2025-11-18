package ort.da.sistema_peajes.peaje.datos;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.exceptions.BonificacionException;
import ort.da.sistema_peajes.peaje.model.Bonificacion.*;

public class SistemaBonificaciones {

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

    public Bonificacion buscarBonificacionNombre(String nombre) throws BonificacionException {
        for (Bonificacion b : this.bonificaciones) {
            if (b.getNombre().equalsIgnoreCase(nombre)) {
                return b;
            }
        }
        
        throw new BonificacionException(nombre);
	}

    public Bonificacion crear_agregarBonificacion(String tipo) {
        Bonificacion b = BonificacionFactory.crear(tipo);
        agregarBonificacion(b);
        return b;
    }

  

}
    