package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Deshabilitado extends EstadoPropietario {

    public Deshabilitado(Propietario propietario) {
        super(propietario);
    }

    @Override
    public void puedeEntrar() throws EstadoException {
        throw new EstadoException("Usuario deshabilitado, no puede ingresar al sistema");
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
        this.getPropietario().cambiarEstado(new Penalizado(getPropietario()));
    }

    @Override
    public void deshabilitado() throws EstadoException {
        throw new EstadoException("Deshabilitado");
    }

    @Override
    public void puedeTransitar() throws EstadoException {
        throw new EstadoException("No puede realizar Transitos");
    }

    @Override
    public boolean bonificable() {
        return false;
    }

    @Override
    public String toString() {
        return "Desabilitado";
    }

    

}
