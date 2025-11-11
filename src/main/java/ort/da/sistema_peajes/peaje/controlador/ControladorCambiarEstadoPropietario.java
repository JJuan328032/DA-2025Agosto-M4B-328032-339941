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
import ort.da.sistema_peajes.peaje.dto.mappers.MapperEstado;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPropietarioBonificacion;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;
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
            return Respuesta.lista(new Respuesta("propietarioBonificacion", 
                MapperPropietarioBonificacion.toDTO(this.propietario)));
        } catch (PropietarioException e) {
            return Respuesta.lista(new Respuesta("error", 
                "No se pudo encontrar un propietario con cédula: " + e.getMessage()));
        }
    }

    @PostMapping("/opciones")
    public List<String> obtenerOpciones(HttpSession sesionHttp) throws Exception {
    ValidarUsuario.validar(sesionHttp, "administrador");
    return Fachada.getInstancia().obtenerEstadosPropietario();

    }

    @PostMapping("/propietario/cambiar")
    public List<Respuesta> cambiarEstado(@RequestParam String cedula, 
                                         @RequestParam String nuevoEstado, 
                                         HttpSession sesionHttp)
            throws PropietarioException, Exception {

        String tipo = "mal";
        String mensaje = "Estado cambiado a: " + nuevoEstado + " con éxito.";
        try {
            ValidarUsuario.validar(sesionHttp, "administrador");
            Fachada.getInstancia().cambiarEstado(cedula, nuevoEstado);
            tipo = "ok";
        } catch (PropietarioException e) {
            mensaje = "No se pudo encontrar un Propietario con nombre: " + e.getMessage();
        } catch (EstadoException e) {
            mensaje = "No se pudo encontrar un Estado con nombre: " + e.getMessage();
        }
        return Respuesta.lista(new Respuesta(tipo, mensaje));
    }

}
