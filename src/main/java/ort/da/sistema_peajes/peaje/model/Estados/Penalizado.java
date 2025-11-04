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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'habilitado'");
    }

    @Override
    public void suspendido() throws EstadoException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'suspendido'");
    }

    @Override
    public void penalizado() throws EstadoException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'penalizado'");
    }

    @Override
    public void deshabilitado() throws EstadoException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deshabilitado'");
    }

    @Override
    public void puedeTransitar() throws EstadoException {
        return;
    }

    @Override
    public boolean bonificable() {
        return false;
    }

}
