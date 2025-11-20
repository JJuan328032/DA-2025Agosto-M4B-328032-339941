package ort.da.sistema_peajes.peaje.controlador;

import java.util.List;


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
import observador.Observable;
import observador.Observador;
import ort.da.sistema_peajes.ConexionNavegador;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPropietario;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperSoloNombre;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.NoEncontradoException;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.model.EventosSistema;
import ort.da.sistema_peajes.peaje.model.Usuarios.Administrador;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.service.Fachada;

@RestController
@RequestMapping("/estado")
@Scope("session")
public class ControladorCambiarEstadoPropietario implements Observador{

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
    
    @PostMapping("/opciones")
    public List<Respuesta> obtenerOpciones(HttpSession sesionHttp) throws Exception {
        
        Administrador a = (Administrador) sesionHttp.getAttribute("administrador");

        if(a == null){
            return Respuesta.lista(new Respuesta("accesoDenegado", "No tiene permisos para acceder aquí"));
        }

        if(this.propietario != null){
            return Respuesta.lista(estadosDefinidos(), propietario());
        }

        return Respuesta.lista();
    }


    @PostMapping("/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula, HttpSession sesionHttp) throws EstadoException, Exception {

        ValidarUsuario.validar(sesionHttp, "administrador");

        try {

            this.propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
            this.propietario.agregarObservador(this);

            return Respuesta.lista(estadosDefinidos(), propietario());

        } catch (PropietarioException e) {
            return Respuesta.lista(new Respuesta("error", "No se pudo encontrar un propietario con cédula: " + e.getMessage()));
        }
    }

    @PostMapping("/cambiar")
    public List<Respuesta> cambiarEstado(@RequestParam String nuevoEstadoNombre, HttpSession sesionHttp) throws Exception{

        ValidarUsuario.validar(sesionHttp, "administrador");

        String tipo = "mal";
        String mensaje;

        if(this.propietario == null){ 
            mensaje = "Debe buscar un Propietario primero";
            return Respuesta.lista(new Respuesta(tipo, mensaje));
        }

        mensaje = "Estado cambiado a: " + nuevoEstadoNombre + " con éxito.";

        try {

            this.propietario.controlCambioEstado(nuevoEstadoNombre);
            tipo = "ok";
            
        } catch (EstadoException e) {
            mensaje = "El usuario ya se encuentra " + e.getMessage();
        } catch(NoEncontradoException e){
            mensaje = "El estado " + e.getMessage() + " no se encuentra";
        }

        return Respuesta.lista(new Respuesta(tipo, mensaje));
    }

    @PostMapping("/vistaCerrada")
    public void salir(){
        if(this.propietario != null){
            this.propietario.quitarObservador(this);
            this.propietario = null;
        }
    }


    private Respuesta propietario(){
        return new Respuesta("propietarioEstado", MapperPropietario.toDTO(this.propietario));
    }

    private Respuesta estadosDefinidos(){
        return new Respuesta("estadosDefinidos", MapperSoloNombre.toDTOlistString(Fachada.getInstancia().obtenerEstadosPropietario()));
    }

    

    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento.equals(EventosSistema.ESTADO_NOTIFICACION)){
            conexionNavegador.enviarJSON(Respuesta.lista(propietario()));
        }
    }


}
