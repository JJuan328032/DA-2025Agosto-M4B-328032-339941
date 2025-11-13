package ort.da.sistema_peajes.peaje.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.datos.SistemaBonificaciones;
import ort.da.sistema_peajes.peaje.datos.SistemaPuestos;
import ort.da.sistema_peajes.peaje.datos.SistemaRegistro;
import ort.da.sistema_peajes.peaje.datos.SistemaVehiculos;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.exceptions.AsignacionException;
import ort.da.sistema_peajes.peaje.exceptions.BonificacionException;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.PuestoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.exceptions.VehiculoException;
import ort.da.sistema_peajes.peaje.datos.SistemaUsuarios;
import ort.da.sistema_peajes.peaje.model.InfoTransito;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Registro;
import ort.da.sistema_peajes.peaje.model.Tarifa;
import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Bonificacion.*;
import ort.da.sistema_peajes.peaje.model.Usuarios.Administrador;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;



public class Fachada {

	private static Fachada instancia;

	private SistemaPuestos sistemaPuestos;
	private SistemaRegistro sistemaRegistro;
	private SistemaVehiculos sistemaVehiculos;
	private SistemaUsuarios sistemaUsuarios;
	private SistemaBonificaciones sistemaBonificaciones;

	public Fachada() {
		this.sistemaPuestos = new SistemaPuestos();
		this.sistemaRegistro = new SistemaRegistro();
		this.sistemaVehiculos = new SistemaVehiculos();
		this.sistemaUsuarios = new SistemaUsuarios();
		this.sistemaBonificaciones = new SistemaBonificaciones();
	}

	public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

	public void agregarPuesto(Puesto peaje1) throws PuestoException{
		sistemaPuestos.agregarPuesto(peaje1);
	}

    public void agregarVehiculo(Vehiculo v1) {
        sistemaVehiculos.agregarVehiculo(v1);
    }

    public void agregarRegistro(Registro r1) {
        sistemaRegistro.agregarRegistro(r1);
    }

	public Bonificacion agregarBonificacion(String tipo) {
		return sistemaBonificaciones.crear_agregarBonificacion(tipo);
	}

	public void agregarAdministrador(String user, String pass, String nombreCompleto, String cedula) {
		sistemaUsuarios.agregarAdministrador(user, pass, nombreCompleto, cedula);
	}

    public Propietario agregarPropietario(String user, String pass, String nombreCompleto, String cedula) {
        return sistemaUsuarios.agregarPropietario(user, pass, nombreCompleto, cedula);
    }


	//LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN LOGIN

	public Propietario loginPropietario(String user, String pass) throws LoginException, EstadoException{
		return sistemaUsuarios.loginPropietario(user, pass);
	}

	public Administrador loginAdministrador(String user, String pass) throws LoginException, EstadoException{
		return sistemaUsuarios.loginAdministrador(user, pass);
	}

    public void logoutAdmin(Administrador a) {
        sistemaUsuarios.logoutAdmin(a);
    }


	//ASIGNAR BONIFICACION ASIGNAR BONIFICACION ASIGNAR BONIFICACION ASIGNAR BONIFICACION ASIGNAR BONIFICACION 

	public ArrayList<Bonificacion> obtenerBonificaciones() {
		return sistemaBonificaciones.getBonificaciones();
	}

	public Propietario buscarPropietarioPorCedula(String cedula) throws PropietarioException {
		return sistemaUsuarios.buscarPropietarioPorCedula(cedula);
	}

	public void asignarBonificaciones(Propietario propietario, String nombreBonificacion, String nombrePuesto) throws PropietarioException, BonificacionException, PuestoException, AsignacionException {
		Bonificacion bonificacion = sistemaBonificaciones.buscarBonificacionNombre(nombreBonificacion);
		Puesto puesto = sistemaPuestos.obtenerPuestoPorNombre(nombrePuesto);

		propietario.agregarAsignacion(bonificacion, puesto);
    }

  	public ArrayList<String> obtenerEstadosPropietario() {
		return sistemaUsuarios.getEstadosPropietario();
	}	


	//EMULAR TRANSITO EMULAR TRANSITO EMULAR TRANSITO EMULAR TRANSITO EMULAR TRANSITO EMULAR TRANSITO EMULAR TRANSITO 

	public ArrayList<Puesto> getPuestos() {
		return sistemaPuestos.getPuestos();
	}

    public ArrayList<Tarifa> obtenerTarifasPorPuestoNombre(String nombre) throws PuestoException{
        return sistemaPuestos.obtenerTarifasPorPuestoNombre(nombre);
    }

	public InfoTransito emularTransito(int indicePuesto, String matricula, LocalDateTime fechaHora) throws SaldoException, EstadoException, VehiculoException, PuestoException, Exception{
		Puesto puesto = sistemaPuestos.obtenerPuestoPorIndice(indicePuesto);
		Vehiculo vehiculo = sistemaVehiculos.obtenerVehiculoPorMatricula(matricula);

		return sistemaRegistro.realizarTransito(puesto, vehiculo, fechaHora);
    }
}
 