package ort.da.sistema_peajes.peaje.model.Usuarios;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;

public class Administrador extends Usuario {

    private boolean logged;


    public Administrador(String usuario, String password, String nombreCompleto, String cedula) {
        super(usuario, password, nombreCompleto, cedula);  
        this.logged = false;
    }

    @Override
    public void Validar() throws EstadoException, LoginException {
        if(logged) throw new LoginException("El administrador ya se encuentra logueado.");
    }

    public void setLogged(boolean status) {
        this.logged = status;
    }


}
