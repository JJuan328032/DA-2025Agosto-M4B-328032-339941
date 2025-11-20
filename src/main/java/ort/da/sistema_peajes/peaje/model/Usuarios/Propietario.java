package ort.da.sistema_peajes.peaje.model.Usuarios;

import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;
import ort.da.sistema_peajes.peaje.model.Estados.Habilitado;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.exceptions.AsignacionException;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.NoEncontradoException;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.EventosSistema;
import ort.da.sistema_peajes.peaje.model.InfoVehiculo;
import ort.da.sistema_peajes.peaje.model.Notificacion;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Registro;

public class Propietario extends Usuario {

	private int saldo;
	private int saldoMinimo;

	private ArrayList<Vehiculo> vehiculos;
	
	private ArrayList<Asignacion> asignaciones;
    private ArrayList<Notificacion> notificaciones;

	private EstadoPropietario estadoPropietario;
    

    public Propietario(String cedula, String password, String nombreCompleto, int saldo, int saldoMinimo) {
        super(cedula, password, nombreCompleto);
        this.vehiculos = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
        this.saldo = saldo;
        this.saldoMinimo = saldoMinimo;
        this.estadoPropietario = new Habilitado(this);
    }


    //usado para mostrar el tipo de estado en tablero CU Tablero de control del propietario
    public String getEstado() {
        return this.estadoPropietario.getNombre();
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

    public void agregarAsignacion(Asignacion a) {
        this.asignaciones.add(a);
    }
    

    public void agregarAsignacion(Bonificacion bonificacion, Puesto puesto) throws AsignacionException, EstadoException{
        this.estadoPropietario.puedeAsignarBono();
        existeAsignacion(puesto);

        Asignacion nueva = new Asignacion(puesto, bonificacion, LocalDate.now());
        this.asignaciones.add(nueva);

        this.avisar(EventosSistema.BONO_ASIGNADO);
    }

    public void agregarNotificacion_PAGO_REALIZADO(Notificacion notificacion) {
        agregarNotificacionAlPrincipio(notificacion);

        if(this.saldo <= this.saldoMinimo) agregarNotificacionAlPrincipio(Notificacion.notificarSaldoBajo(saldo));

        this.avisar(EventosSistema.NOTIFICACION);
    }



	@Override
	public void Validar() throws EstadoException, LoginException {
		this.estadoPropietario.puedeEntrar();
	}


    public ArrayList<InfoVehiculo> obtenerInfoVehiculos(){
        ArrayList<InfoVehiculo> lista = new ArrayList<>();

        for (Vehiculo v : this.vehiculos) {
            lista.add(v.obtenerRegistrosPorVehiculo());
        }

        return lista;
    }


    public Asignacion buscarAsignacionSegunPuesto(Puesto puesto) {
        for(Asignacion a : this.asignaciones){
            if(a.equals(puesto)) return a;
        }

        return null;
    }

    private void existeAsignacion(Puesto buscada) throws AsignacionException{
        for(Asignacion a : this.asignaciones) if(a.equals(buscada)) throw new AsignacionException("Asigancion existente");
    }

    
    public void restarMonto(double monto){
        this.saldo -= monto;
    }

    public void cambiarEstado(EstadoPropietario nuevo) {
        this.estadoPropietario = nuevo;
        agregarNotificacionAlPrincipio(Notificacion.notificarEstado(nuevo.getNombre()));

        this.avisar(EventosSistema.ESTADO_NOTIFICACION);
    }

    //usado en SeedData
    public void agregarNotificacion(String string) {
        agregarNotificacionAlPrincipio(new Notificacion(string));
    }

    private void agregarNotificacionAlPrincipio(Notificacion n){
        this.notificaciones.add(0, n);
    }


    public void controlCambioEstado(String nombreEstado) throws NoEncontradoException, EstadoException {
        this.estadoPropietario.cambiarEstado(nombreEstado);
    }


    public void borrarNotificaciones() throws PropietarioException{
        if(this.notificaciones.isEmpty()) throw new PropietarioException("");
        this.notificaciones.clear();
    }


    public ArrayList<Registro> getRegistros() {
        ArrayList<Registro> registrosTotales = new ArrayList<>();
        for (Vehiculo v : this.vehiculos) {
            registrosTotales.addAll(v.getRegistros());
        }

        return registrosTotales;
    }

}
