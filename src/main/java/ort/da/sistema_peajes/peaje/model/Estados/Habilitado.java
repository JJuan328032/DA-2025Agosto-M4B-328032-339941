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
        // ya esta habilitado
    }

    @Override
    public void suspendido() throws EstadoException {
        throw new EstadoException("No se puede suspender un propietario habilitado directamente. Debe ser penalizado primero.");
    }

    @Override
    public void penalizado() throws EstadoException {
        throw new EstadoException("No se puede penalizar un propietario habilitado directamente. Debe ser suspendido primero.");
    }   

    @Override
    public void deshabilitado() throws EstadoException {
        throw new EstadoException("No se puede deshabilitar un propietario habilitado directamente. Debe ser penalizado primero.");
    }


    @Override
    public void puedeTransitar() throws EstadoException {
        return;
    }


}
