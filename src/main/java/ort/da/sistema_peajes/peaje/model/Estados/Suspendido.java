package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Suspendido extends EstadoPropietario {

    public Suspendido(Propietario propietario) {
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
        throw new EstadoException("Suspendido");
    }

    @Override
    public void penalizado() throws EstadoException {
        this.getPropietario().cambiarEstado(new Penalizado(getPropietario()));
    }

    @Override
    public void deshabilitado() throws EstadoException {
        this.getPropietario().cambiarEstado(new Deshabilitado(getPropietario()));
    }

    @Override
    public void puedeTransitar() throws EstadoException {
        throw new EstadoException("Se encuentra actualmente Suspendido. No puede realizar Transitos");
    }

    @Override
    public boolean bonificable() {
        return false;
    }

    @Override
    public String toString() {
        return "Suspendido";
    }
}
