package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Registro;
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
        throw new EstadoException("Ya se encuentra Deshabilitado");
    }

    @Override
    public void puedeTransitar() throws EstadoException {
        throw new EstadoException("Deshabilitado. No puede realizar Tránsitos");
    }

    @Override
    public boolean bonificable() {
        return false;
    }

    @Override
    public String getNombre() {
        return "Deshabilitado";
    }

    @Override
    public void puedeAsignarBono() throws EstadoException {
        throw new EstadoException("El propietario esta deshabilitado. No se pueden asignar bonificaciones");
    }

    @Override
    public void procesarPagoSimple(Pagar pagar, Registro registro)
            throws EstadoException, SaldoException {
        
        this.puedeTransitar();
    }

}
