package ort.da.sistema_peajes.peaje.controlador;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Administrador;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.service.Fachada;

@RestController
@RequestMapping("/acceso")

public class ControladorLogin {

    @PostMapping("/loginPropietario")
    public List<Respuesta> loginPropietario(HttpSession sesionHttp, @RequestParam String user, @RequestParam String pass) throws LoginException, EstadoException {
        // Lógica para autenticar al propietario

        try {
            Propietario p = Fachada.getInstancia().loginPropietario(user, pass);
        
            sesionHttp.setAttribute("propietario", p);
            return Respuesta.lista(new Respuesta("LoginExitoso", "tablero_control_propietario.html"));
        } catch (LoginException e) {
            throw new LoginException("Credenciales inválidas para propietario.");
        }

    }

    @PostMapping("/loginAdministrador")
    public List<Respuesta> loginAdministrador(HttpSession sesionHttp, @RequestParam String user, @RequestParam String pass) throws LoginException, EstadoException{
        // Lógica para autenticar al propietario

        try {
            Administrador a = Fachada.getInstancia().loginAdministrador(user, pass);

        sesionHttp.setAttribute("administrador", a);
        return Respuesta.lista(new Respuesta("LoginExitoso", "menu_administrador.html"));

        } catch (LoginException e) {
            throw new LoginException("Credenciales inválidas para administrador.");

        }

    }

    @PostMapping("/salirAdministrador")
    public List<Respuesta> salirAdministrador(HttpSession sesionHttp){
        // Lógica para autenticar al propietario
        Administrador a = (Administrador) sesionHttp.getAttribute("administrador");
        if(a!=null){
            Fachada.getInstancia().logoutAdmin(a);
            sesionHttp.removeAttribute("administrador");
        }

        return Respuesta.lista(new Respuesta("LogoutExitoso", "login_administrador.html"));
    }
}