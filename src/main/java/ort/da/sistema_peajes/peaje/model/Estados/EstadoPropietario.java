package ort.da.sistema_peajes.peaje.model.Estados;


import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.NoEncontradoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Registro;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public abstract class EstadoPropietario {

    private Propietario propietario;

    public EstadoPropietario(Propietario propietario) {
        this.propietario = propietario;
    }

    public Propietario getPropietario(){ return this.propietario;}


    public abstract void habilitado() throws EstadoException;
    public abstract void suspendido() throws EstadoException;
    public abstract void penalizado() throws EstadoException;
    public abstract void deshabilitado() throws EstadoException;

    public abstract void puedeEntrar() throws EstadoException;
    public abstract boolean bonificable();
    public abstract void puedeTransitar() throws EstadoException;
    public abstract String getNombre();
    public abstract void puedeAsignarBono() throws EstadoException;

    public abstract void procesarPagoSimple(Pagar pagar, Registro registro) throws EstadoException, SaldoException;

    public void cambiarEstado(String nombreEstado) throws NoEncontradoException, EstadoException {
        switch (nombreEstado) {
            case "Deshabilitado": deshabilitado(); break;
            case "Habilitado": habilitado(); break;
            case "Penalizado": penalizado(); break;
            case "Suspendido": suspendido(); break;
        
            default: throw new NoEncontradoException(nombreEstado);
        }
    }

}


