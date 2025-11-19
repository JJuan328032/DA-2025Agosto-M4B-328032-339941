package ort.da.sistema_peajes.peaje.model.Usuarios;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import observador.Observable;

public abstract class Usuario extends Observable {

	private String cedula;	
	private String password;
	private String nombreCompleto;
	


	public Usuario(String cedula, String password, String nombreCompleto) {
		this.cedula = cedula;
		this.password = password;
		this.nombreCompleto = nombreCompleto;
	}

	abstract public void Validar() throws EstadoException ,LoginException;


	public String getNombreCompleto() {
		return this.nombreCompleto;
	}

	public String getCedula() {	
		return this.cedula;
	}

	
	public boolean validarCredenciales(String cedula, String password) throws EstadoException, LoginException{
		return this.cedula.equals(cedula) && this.password.equals(password);
	}

	public boolean validarCedula(String cedula) {
		return this.cedula != null && this.cedula.equals(cedula);
	}

}
