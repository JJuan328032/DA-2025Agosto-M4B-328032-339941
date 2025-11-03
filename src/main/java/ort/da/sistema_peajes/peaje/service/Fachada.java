package ort.da.sistema_peajes.peaje.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.cglib.core.Local;

import ort.da.sistema_peajes.peaje.datos.SistemaBonificaciones;
import ort.da.sistema_peajes.peaje.datos.SistemaPuestos;
import ort.da.sistema_peajes.peaje.datos.SistemaRegistro;
import ort.da.sistema_peajes.peaje.datos.SistemaVehiculos;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.datos.SistemaUsuarios;
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Bonificacion.*;
import ort.da.sistema_peajes.peaje.model.Usuarios.Administrador;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.model.Usuarios.Usuario;



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

	/**
	 * return login.login(usuario, password);
	 */
	public Usuario login(LoginUsuario login, String usuario, String password) {
		return login.login(usuario, password);
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

	
	public Puesto agregarPuesto(String nombre, String direccion) {
    Puesto p = new Puesto(nombre, direccion);
    sistemaPuestos.agregarPuesto(p);
	return p;
	}

	public List<Puesto> obtenerPuestos() {
    List<Puesto> puestos = new ArrayList<>(sistemaPuestos.getPuestos());
    System.out.println("Cantidad de puestos cargados en la Fachada: " + puestos.size());
    return puestos;
	}


	 public Bonificacion agregarBonificacion(int i, String descripcion) {
		Bonificacion b = new Descuento(i, descripcion);
		sistemaBonificaciones.agregarBonificacion(b);
		return b;
	}

	public List<Bonificacion> obtenerBonificaciones() {
		List<Bonificacion> bonificaciones = new ArrayList<>(sistemaBonificaciones.getBonificaciones());
		System.out.println("Cantidad de bonificaciones cargadas en la Fachada: " + bonificaciones.size());
		return bonificaciones;

	}

	public Propietario buscarPropietarioPorCedula(String cedula) throws LoginException, EstadoException {
		Propietario p = sistemaUsuarios.buscarPropietarioPorCedula(cedula);
		if (p == null) {
			return null;
		}
		return p;
		
	}
	
	public List<Asignacion> obtenerAsignacionesDePropietario(Propietario encontrado) {
		List<Asignacion> asignaciones = sistemaUsuarios.obtenerAsignacionesDePropietario(encontrado);
		System.out.println("Cantidad de Asignaciones del usuario: " + asignaciones.size());
		return asignaciones;
	}

    public boolean asignarBonificaciones(Propietario propietario, String Bonificacion,String Puesto) throws EstadoException {
		Bonificacion bonificacion = sistemaBonificaciones.obtenerBonificacionByNombre(Bonificacion);
		Puesto puesto = sistemaPuestos.obtenerPuestoByNombre(Puesto);

		System.out.println("🏗️ [FACHADA] Asignando bonificación...");
    	System.out.println("   Propietario: " + (propietario != null ? propietario.getNombreCompleto() : "null"));
    	System.out.println("   Bonificacion: " + bonificacion);
    	System.out.println("   Puesto: " + puesto);
		
		if (bonificacion == null || puesto == null) {
			return false;
		}
		Asignacion asignacion = new Asignacion(puesto,bonificacion,LocalDate.now());
		propietario.agregarAsignacion(asignacion);

        return true;
    }


}
 