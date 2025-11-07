package ort.da.sistema_peajes.peaje.model.Usuarios;

import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;
import ort.da.sistema_peajes.peaje.model.Estados.Habilitado;
import ort.da.sistema_peajes.peaje.model.Registro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.exceptions.AsignacionException;
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


	public Propietario(String usuario, String password, String nombreCompleto, String cedula) {
        super(usuario, password, nombreCompleto, cedula);
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

    public void agregarAsignacion(Bonificacion obtenerBonificacionByNombre, Puesto obtenerPuestoPorNombre) throws AsignacionException{
        Asignacion nueva = new Asignacion(obtenerPuestoPorNombre, obtenerBonificacionByNombre, LocalDate.now());

        existeAsignacion(nueva);
        this.asignaciones.add(nueva);
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


    public String realizarPago(Registro registro) throws SaldoException, EstadoException{

        if(validarEstado()){
            //validar si tengo una bonificacion en el puesto
            Asignacion a = this.buscarAsignacionSegunPuesto(registro.getPuesto());

            //si la tengo, seteo el montoBonificado previamente inicializado en cero
            if(a != null) registro.setMontoBonificado(a.calcularMontoBonificado(registro.getMontoTarifa(), this.esSegundoTransitoDelDia(registro.getPuesto(), registro.getVehiculo(), registro.getFecha())));
            
            completarRegistro(registro);

            //retorna Asignacion porque solo el Propietario sabe si tiene bonificacion en el Puesto y se necesita el nombre de dicho bono
            return a.getBonificacionNombre();
        }else{

            completarRegistro(registro);
        }

        return null;
    }

    private void completarRegistro(Registro registro) throws SaldoException{
        descontarTransito(registro.calcularPrecioFinal());
        registro.setMontoPagado();

        this.agregarRegistro(registro);
    }

    private void descontarTransito(double monto) throws SaldoException{
        if(this.saldo - monto < 0) throw new SaldoException("Saldo insuficiente: " + this.saldo + " para cobrar el total: " + monto);

        //funciona si es Exonerado y el saldo es cero
        this.saldo -= monto;
    }

    private boolean validarEstado() throws EstadoException{
        //si no puede, manda exception y corta ejecucion
        this.estadoPropietario.puedeTransitar();

        //si no puede recibir bonificaciones registro se encarga
        return this.estadoPropietario.bonificable();
    }

    private Asignacion buscarAsignacionSegunPuesto(Puesto puesto) {
        for(Asignacion a : this.asignaciones){
            if(a.equals(puesto)) return a;
        }

        return null;
    }

    private void existeAsignacion(Asignacion buscada) throws AsignacionException{
        for(Asignacion a : this.asignaciones) if(a.equals(buscada)) throw new AsignacionException("");
    }

    private boolean esSegundoTransitoDelDia(Puesto puesto, Vehiculo vehiculo, LocalDateTime fecha) {
		int cont = 0;

		for(int i = 0; i < this.registros.size() && cont < 2; i++){
			if(this.registros.get(i).validarMismoDia(puesto, vehiculo, fecha)) cont++;
		}

		return cont == 2;
	}

}
