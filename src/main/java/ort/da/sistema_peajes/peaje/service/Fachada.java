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
import ort.da.sistema_peajes.peaje.model.Asignacion;
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

	public Propietario loginPropietario(String user, String pass) throws LoginException, EstadoException{
		return sistemaUsuarios.loginPropietario(user, pass);
	}

	public Administrador loginAdministrador(String user, String pass) throws LoginException, EstadoException{
		return sistemaUsuarios.loginAdministrador(user, pass);
	}

	public void agregarAdministrador(String user, String pass, String nombreCompleto, String cedula) {
		sistemaUsuarios.agregarAdministrador(user, pass, nombreCompleto, cedula);
	}

    public Propietario agregarPropietario(String user, String pass, String nombreCompleto, String cedula) {
        return sistemaUsuarios.agregarPropietario(user, pass, nombreCompleto, cedula);
    }

    public void logoutAdmin(Administrador a) {
        sistemaUsuarios.logoutAdmin(a);
    }

    public ArrayList<Tarifa> obtenerTarifasPorPuestoNombre(String nombre) throws PuestoException{
        return sistemaPuestos.obtenerTarifasPorPuestoNombre(nombre);
    }

	public ArrayList<Puesto> getPuestos() {
		return sistemaPuestos.getPuestos();
	}

	public void asociarVehiculoAPropietario(Vehiculo v, Propietario p) {
		sistemaVehiculos.asociarVehiculoAPropietario(v, p);
		sistemaUsuarios.agregarVehiculoPropietario(v, p);
	}

    public InfoTransito emularTransito(int indicePuesto, String matricula, LocalDateTime fechaHora) throws SaldoException, EstadoException, VehiculoException, Exception{
		return sistemaRegistro.realizarTransito(sistemaPuestos.obtenerPuestoPorIndice(indicePuesto), sistemaVehiculos.obtenerVehiculoPorMatricula(matricula), fechaHora);
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
		return sistemaBonificaciones.agregarBonificacion(tipo);
	}

	public ArrayList<Bonificacion> obtenerBonificaciones() {
		//List<Bonificacion> bonificaciones = new ArrayList<>(sistemaBonificaciones.getBonificaciones());
		//System.out.println("Cantidad de bonificaciones cargadas en la Fachada: " + bonificaciones.size());
		//return bonificaciones;
		return sistemaBonificaciones.getBonificaciones();
	}

	public Propietario buscarPropietarioPorCedula(String cedula) throws PropietarioException {
		//Propietario p = sistemaUsuarios.buscarPropietarioPorCedula(cedula);
		//if (p == null) {
		//	return null;
		//}
		//return p;
		return sistemaUsuarios.buscarPropietarioPorCedula(cedula);
	}
	
	public ArrayList<Asignacion> obtenerAsignacionesDePropietario(Propietario encontrado) {
		//List<Asignacion> asignaciones = sistemaUsuarios.obtenerAsignacionesDePropietario(encontrado);
		//System.out.println("Cantidad de Asignaciones del usuario: " + asignaciones.size());
		//return asignaciones;
		return sistemaUsuarios.obtenerAsignacionesDePropietario(encontrado);
	}

    public void asignarBonificaciones(String cedulaPropietario, String nombreBonificacion, String nombrePuesto) throws PropietarioException, BonificacionException, PuestoException, AsignacionException {
		/*
		Bonificacion bonificacion = sistemaBonificaciones.obtenerBonificacionByNombre(Bonificacion);
		Puesto puesto = sistemaPuestos.obtenerPuestoPorNombre(Puesto);

		System.out.println("🏗️ [FACHADA] Asignando bonificación...");
    	System.out.println("   Propietario: " + (propietario != null ? propietario.getNombreCompleto() : "null"));
    	System.out.println("   Bonificacion: " + bonificacion);
    	System.out.println("   Puesto: " + puesto);
		
		//no estamos creando la bonificacion actualmente, podria devolver null
		if (bonificacion == null || puesto == null) {
			return false;
		}

		Asignacion asignacion = new Asignacion(puesto, bonificacion, LocalDate.now());
		propietario.agregarAsignacion(asignacion);

        return true;
		*/

		sistemaUsuarios.buscarPropietarioPorCedula(cedulaPropietario).agregarAsignacion(sistemaBonificaciones.obtenerBonificacionByNombre(nombreBonificacion), sistemaPuestos.obtenerPuestoPorNombre(nombrePuesto));
    }


}
 