package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Penalizado extends EstadoPropietario {

    public Penalizado(Propietario propietario) {
        super(propietario);
    }

    @Override
    public void puedeEntrar() throws EstadoException {
        return;
    }

    @Override
    public void habilitado() throws EstadoException {
        this.getPropietario().cambiarEstado(new Habilitado(getPropietario()));
    }

    @Override
    public void suspendido() throws EstadoException {
        this.getPropietario().cambiarEstado(new Suspendido(getPropietario()));
    }

    @Override
    public void penalizado() throws EstadoException {
        throw new EstadoException("Penalizado");
    }

    @Override
    public void deshabilitado() throws EstadoException {
        this.getPropietario().cambiarEstado(new Deshabilitado(getPropietario()));
    }

    @Override
    public void puedeTransitar() throws EstadoException {
        return;
    }

    @Override
    public boolean bonificable() {
        return false;
    }

    @Override
    public String toString() {
        return "Penalizado";
    }

}
