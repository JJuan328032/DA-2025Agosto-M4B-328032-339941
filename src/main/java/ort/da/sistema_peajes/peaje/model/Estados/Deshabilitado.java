package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Deshabilitado extends EstadoPropietario {

    public Deshabilitado(Propietario propietario) {
        super(propietario);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void puedeEntrar() throws EstadoException {
        throw new EstadoException("Usuario deshabilitado, no puede ingresar al sistema");
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
        throw new EstadoException("No puede realizar Transitos");
    }

    @Override
    public boolean bonificable() {
        return false;
    }

    

}
