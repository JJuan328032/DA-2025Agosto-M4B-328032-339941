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

	public Propietario loginPropietario(String usuario, String password) throws LoginException, EstadoException {
		return login(usuario, password, this.propietarios);
	}

	public Administrador loginAdministrador(String usuario, String password) throws LoginException, EstadoException{
		Administrador administrador = login(usuario, password, administradores);
		administrador.setLogged(true);
		
		return administrador;
	}

    public void agregarAdministrador(String user, String pass, String nombreCompleto, String cedula) {
        this.administradores.add(new Administrador(user, pass, nombreCompleto,cedula));
    }


    public Propietario agregarPropietario(String user, String pass, String nombreCompleto, String cedula) {
		Propietario p = new Propietario(user, pass, nombreCompleto, cedula);
        this.propietarios.add(p);

		return p;
    }


	private <T extends Usuario> T login(String usuario, String password, ArrayList<T> lista) throws LoginException, EstadoException{
		T u = buscarUsuario(usuario, password, lista);
		u.Validar();

		return u;
	}

	private <T extends Usuario> T buscarUsuario(String usuario, String password, ArrayList<T> lista) throws LoginException, EstadoException{
		for (T u : lista) if (u.validarCredenciales(usuario, password)) return u;

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
