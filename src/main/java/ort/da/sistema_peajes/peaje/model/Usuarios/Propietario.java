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
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.EventosSistema;
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
        return this.estadoPropietario.toString();
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

    public ArrayList<Notificacion> getNotificaciones() {
        return this.notificaciones;
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
        this.avisar(EventosSistema.TRANSITO_REALIZADO);
    }

    public void agregarAsignacion(Asignacion a) {
        this.asignaciones.add(a);
    }

    public void agregarAsignacion(Bonificacion bonificacion, Puesto puesto) throws AsignacionException{
        Asignacion nueva = new Asignacion(puesto, bonificacion, LocalDate.now());

        existeAsignacion(puesto);
        this.asignaciones.add(nueva);
        //this.notificaciones.add(Notificacion.notificarBonificacion(bonificacion.getNombre()));

        // se puede mejorar para avisar una sola vez?
        this.avisar(EventosSistema.BONO_ASIGNADO);
        //this.avisar(EventosSistema.NOTIFICACION);
    }

    public void agregarNotificacion(Notificacion notificacion) {
        agregarNotificacionAlPrincipio(notificacion);

        if(this.saldo < this.saldoMinimo) agregarNotificacionAlPrincipio(Notificacion.notificarSaldoBajo(saldo));

        this.avisar(EventosSistema.NOTIFICACION);
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

    public boolean validarEstado() throws EstadoException{
        //si no puede, manda exception y corta ejecucion
        this.estadoPropietario.puedeTransitar();

        //si no puede recibir bonificaciones, registro se encarga
        return this.estadoPropietario.bonificable();
    }

    public Asignacion buscarAsignacionSegunPuesto(Puesto puesto) {
        for(Asignacion a : this.asignaciones){
            if(a.equals(puesto)) return a;
        }

        return null;
    }

    private void existeAsignacion(Puesto buscada) throws AsignacionException{
        for(Asignacion a : this.asignaciones) if(a.equals(buscada)) throw new AsignacionException("");
    }

    public boolean esSegundoTransitoDelDia(Puesto puesto, Vehiculo vehiculo, LocalDateTime fecha) {
		int cont = 0;

		for(int i = 0; i < this.registros.size() && cont < 2; i++){
			if(this.registros.get(i).validarMismoDia(puesto, vehiculo, fecha)) cont++;
		}

		return cont == 2;
	}

    public void restarMonto(double monto){
        this.saldo -= monto;
    }


    public void cambiarEstado(EstadoPropietario nuevo) {
        this.estadoPropietario = nuevo;
        agregarNotificacionAlPrincipio(Notificacion.notificarEstado(nuevo.toString()));

        //TODO reorganizar para avisar una sola vez
        this.avisar(EventosSistema.ESTADO_NOTIFICACION);
    }

    //usado en SeedData
    public void agregarNotificacion(String string) {
        agregarNotificacionAlPrincipio(new Notificacion(string));
    }

    private void agregarNotificacionAlPrincipio(Notificacion n){
        this.notificaciones.add(0, n);
    }
}
