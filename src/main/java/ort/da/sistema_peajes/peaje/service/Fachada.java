package ort.da.sistema_peajes.peaje.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.datos.SistemaPuestos;
import ort.da.sistema_peajes.peaje.datos.SistemaRegistro;
import ort.da.sistema_peajes.peaje.datos.SistemaVehiculos;
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
import ort.da.sistema_peajes.peaje.model.Usuarios.Administrador;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.model.Usuarios.Usuario;


public class Fachada {

	private static Fachada instancia;

	private SistemaPuestos sistemaPuestos;
	private SistemaRegistro sistemaRegistro;
	private SistemaVehiculos sistemaVehiculos;
	private SistemaUsuarios sistemaUsuarios;

	public Fachada() {
		this.sistemaPuestos = new SistemaPuestos();
		this.sistemaRegistro = new SistemaRegistro();
		this.sistemaVehiculos = new SistemaVehiculos();
		this.sistemaUsuarios = new SistemaUsuarios();
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

	public void agregarAdministrador(String user, String pass, String nombreCompleto) {
		sistemaUsuarios.agregarAdministrador(user, pass, nombreCompleto);
	}

    public Propietario agregarPropietario(String user, String pass, String nombreCompleto) {
        return sistemaUsuarios.agregarPropietario(user, pass, nombreCompleto);
    }

    public void logoutAdmin(Administrador a) {
        sistemaUsuarios.logoutAdmin(a);
    }

    public ArrayList<Tarifa> obtenerTarifasPorPuestoNombre(String nombre) {
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


	

}
