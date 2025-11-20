package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Registro;
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
        throw new EstadoException("Ya se encuentra Suspendido");
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
        throw new EstadoException("Suspendido. No puede realizar Tránsitos");
    }

    @Override
    public boolean bonificable() {
        return false;
    }

    @Override
    public String getNombre() {
        return "Suspendido";
    }

    @Override
    public void puedeAsignarBono() throws EstadoException {
        return;
    }

    @Override
    public void procesarPagoSimple(Pagar pagar, Registro registro)
            throws EstadoException, SaldoException {
        
        this.puedeTransitar();
    }
}
