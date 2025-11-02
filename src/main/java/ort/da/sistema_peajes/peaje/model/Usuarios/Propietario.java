package ort.da.sistema_peajes.peaje.model.Usuarios;

import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;
import ort.da.sistema_peajes.peaje.model.Estados.Habilitado;
import ort.da.sistema_peajes.peaje.model.Registro;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;


import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.InfoVehiculo;
import ort.da.sistema_peajes.peaje.model.Notificacion;
import ort.da.sistema_peajes.peaje.model.Puesto;

public class Propietario extends Usuario {

	private int saldo;
	private int saldoMinimo;

	private ArrayList<Vehiculo> vehiculos;
	private ArrayList<Registro> registros;
	private ArrayList<Asignacion> asignaciones;
    private ArrayList<Notificacion> notificaciones;

	private EstadoPropietario estadoPropietario;


	public Propietario(String cedula, String password, String nombreCompleto) {
        super(cedula, password, nombreCompleto);
        this.vehiculos = new ArrayList<>();
        this.registros = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
        this.saldo = 0;
        this.saldoMinimo = 1000;
        this.estadoPropietario = new Habilitado(this);
    }


    //usado para mostrar el tipo de estado en tablero CU Tablero de control del propietario
    public String getEstado() {
        return "Por Hacer";
    }

    public int getSaldo() {
        return this.saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldoMinimo() {
        return this.saldoMinimo;
    }

    public void setSaldoMinimo(int saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }

    public ArrayList<Registro> getRegistros() {
        return this.registros;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return this.asignaciones;
    }

    public EstadoPropietario getEstadoPropietario() {
        return this.estadoPropietario;
    }

    public void setEstadoPropietario(EstadoPropietario estadoPropietario) {
        this.estadoPropietario = estadoPropietario;
    }

    public void agregarVehiculo(Vehiculo v) {
        this.vehiculos.add(v);
    }

    public void agregarRegistro(Registro r) {
        this.registros.add(r);
    }

    public void agregarAsignacion(Asignacion a) {
        this.asignaciones.add(a);
    }

    public void agregarNotificacion(String mensaje) {
        notificaciones.add(new Notificacion(mensaje));
    }


	@Override
	public void Validar() throws EstadoException, LoginException {
		this.estadoPropietario.puedeEntrar();
	}




    public ArrayList<InfoVehiculo> obtenerInfoVehiculos(){
        ArrayList<InfoVehiculo> lista = new ArrayList<>();

        for (Vehiculo v : this.vehiculos) {
            lista.add(obtenerRegistrosPorVehiculo(v));
        }

        return lista;
    }

    //cuantos registros existen de determinado vehiculo, guardar cantidad y monto total
	private InfoVehiculo obtenerRegistrosPorVehiculo(Vehiculo v) {
		int contador = 0;
		double montoTotal = 0.0;

		for (Registro r : this.registros) {
			if (r.getVehiculo().equals(v)) {
				contador++;
				montoTotal += r.getMontoPagado();
			}
		}
        
		return new InfoVehiculo(v, contador, montoTotal);
	}


    public Asignacion realizarPago(Registro registro) throws SaldoException, EstadoException{
        //validar si tengo una bonificacion en el puesto
        Asignacion a = this.buscarAsignacionSegunPuesto(registro.getPuesto());

        //si la tengo, seteo el montoBonificado previamente inicializado en cero
        if(a != null) registro.setMontoBonificado(a.calcularMontoBonificado(registro.getMontoTarifa()));
        
        validarRealizarTransito(registro.calcularPrecioFinal());
        registro.setMontoPagado();
        //modifico Registro desde acá
        //Hay que completar el nombre de la bonificacion y validar el estado del saldo
        //Validar por Estado también

        this.agregarRegistro(registro);

        //retorna Asignacion porque solo el Propietario sabe si tiene bonificacion en el Puesto y se necesita el nombre de dicho bono
        return a;
    }

    private void validarRealizarTransito(int monto) throws SaldoException, EstadoException{
        //de no estar habilitado no se descuenta 
        validarEstado();

        //como el 
        descontarTransito(monto);
    }

    private void descontarTransito(int monto) throws SaldoException{
        if(this.saldo - monto < 0) throw new SaldoException("Saldo insuficiente: " + this.saldo + " para cobrar el total: " + monto);

        //cuando vaya a descontar tambien hay que cambar el campo montoPagado de Registro
        //funciona si es Exonerado y el saldo es cero
        this.saldo -= monto;
    }

    private void validarEstado() throws EstadoException{
        this.estadoPropietario.puedeTransitar();
    }

    private Asignacion buscarAsignacionSegunPuesto(Puesto puesto) {
        for(Asignacion a : this.asignaciones){
            if(a.equals(puesto)) return a;
        }

        return null;
    }

    

}
