package ort.da.sistema_peajes.peaje.controlador;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpSession;
import ort.da.sistema_peajes.ConexionNavegador;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPropietarioBonificacion;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.service.Fachada;

@RestController
@RequestMapping("/estado")
@Scope("session")
public class ControladorCambiarEstadoPropietario {

    private final ConexionNavegador conexionNavegador; 
    private Propietario propietario;

    public ControladorCambiarEstadoPropietario(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

     @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
    }
    

    @PostMapping("/propietario/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula, HttpSession sesionHttp) 
            throws LoginException, EstadoException, Exception {
        ValidarUsuario.validar(sesionHttp, "administrador");
        try {
            this.propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
            return Respuesta.lista(new Respuesta("propietarioEstado", 
                MapperPropietarioBonificacion.toDTO(this.propietario)));
        } catch (PropietarioException e) {
            return Respuesta.lista(new Respuesta("error", 
                "No se pudo encontrar un propietario con cédula: " + e.getMessage()));
        }
    }

  @PostMapping("/opciones")
    public List<Respuesta> obtenerOpciones(HttpSession sesionHttp) throws Exception {
        ValidarUsuario.validar(sesionHttp, "administrador");

        List<String> estados = Fachada.getInstancia().obtenerEstadosPropietario();
    return Respuesta.lista(new Respuesta("estadosDefinidos", estados));
}

    @PostMapping("/propietario/cambiar")
    public List<Respuesta> cambiarEstado(@RequestParam String cedula, 
                                     @RequestParam String nuevoEstado, 
                                     HttpSession sesionHttp) {

        String tipo = "mal";
        String mensaje = "";
        try {
            ValidarUsuario.validar(sesionHttp, "administrador");
            Fachada.getInstancia().cambiarEstado(cedula, nuevoEstado);
            tipo = "ok";
            mensaje = "Estado cambiado a: " + nuevoEstado + " con éxito.";
        } catch (PropietarioException e) {
            mensaje = "No se pudo encontrar un Propietario: " + e.getMessage();
        } catch (EstadoException e) {
            mensaje = "No se pudo encontrar un Estado: " + e.getMessage();
        } catch (Exception e) {
            mensaje = "Ocurrió un error interno al cambiar el estado: " + e.getMessage();
        }

        return Respuesta.lista(new Respuesta(tipo, mensaje));
    }


}
