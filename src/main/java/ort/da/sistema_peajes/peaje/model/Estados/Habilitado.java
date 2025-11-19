package ort.da.sistema_peajes.peaje.model.Estados;


import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Habilitado extends EstadoPropietario {

    public Habilitado(Propietario propietario) {
        super(propietario);
    }

    @Override
    public void puedeEntrar() throws EstadoException {
        return;
    }

    @Override
    public void habilitado() throws EstadoException {
        throw new EstadoException("Habilitado");
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
        this.getPropietario().cambiarEstado(new Deshabilitado(getPropietario()));
    }


    @Override
    public void puedeTransitar() throws EstadoException {
        return;
    }


    @Override
    public boolean bonificable() {
        return true;
    }


    @Override
    public String getNombre() {
        return "Habilitado";
    }

    @Override
    public void puedeAsignarBono() throws EstadoException {
        return;
    }
}
