package ort.da.sistema_peajes.peaje.datos;

import ort.da.sistema_peajes.peaje.model.Usuarios.Usuario;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Administrador;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;


public class SistemaUsuarios {

	private ArrayList<Propietario> propietarios;
	private ArrayList<Administrador> administradores;

	private ArrayList<String> estadosPropietario;

	public SistemaUsuarios() {
		this.propietarios = new ArrayList<>();
		this.administradores = new ArrayList<>();
		this.estadosPropietario = new ArrayList<>();

		this.poblarEstados();
	}

	private void poblarEstados(){
		this.estadosPropietario.add("Habilitado");
		this.estadosPropietario.add("Deshabilitado");
		this.estadosPropietario.add("Penalizado");
		this.estadosPropietario.add("Suspendido");
	}

	public ArrayList<String> getEstadosPropietario(){ return this.estadosPropietario;}

	public Propietario loginPropietario(String cedula, String password) throws LoginException, EstadoException {
		return login(cedula, password, this.propietarios);
	}

	public Administrador loginAdministrador(String cedula, String password) throws LoginException, EstadoException{
		Administrador administrador = login(cedula, password, administradores);
		administrador.setLogged(true);
		
		return administrador;
	}

    public void agregarAdministrador(String cedula, String pass, String nombreCompleto) {
        this.administradores.add(new Administrador(cedula, pass, nombreCompleto));
    }


    public Propietario agregarPropietario(String cedula, String pass, String nombreCompleto, int saldo, int saldoMinimo) {
		Propietario p = new Propietario(cedula, pass, nombreCompleto, saldo, saldoMinimo);
        this.propietarios.add(p);

		return p;
    }


	private <T extends Usuario> T login(String cedula, String password, ArrayList<T> lista) throws LoginException, EstadoException{
		T u = buscarUsuario(cedula, password, lista);
		u.Validar();

		return u;
	}

	private <T extends Usuario> T buscarUsuario(String cedula, String password, ArrayList<T> lista) throws LoginException, EstadoException{
		for (T u : lista) if (u.validarCredenciales(cedula, password)) return u;

		throw new LoginException("Acceso denegado");
	}

    public void logoutAdmin(Administrador a) {
        a.setLogged(false);
    }

	private <T extends Usuario> T buscarUsuarioCedula(String cedula, ArrayList<T> lista) throws PropietarioException {
		for (T u : lista) {
			if (u.validarCedula(cedula)) {
				return u;
			}
		}

		throw new PropietarioException(cedula);
	}


	public Propietario buscarPropietarioPorCedula(String cedula) throws PropietarioException{
		return buscarUsuarioCedula(cedula, this.propietarios);
	}

}
