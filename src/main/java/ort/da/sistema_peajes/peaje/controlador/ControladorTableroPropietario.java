package ort.da.sistema_peajes.peaje.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import jakarta.servlet.http.HttpSession;
import observador.Observable;
import observador.Observador;
import ort.da.sistema_peajes.ConexionNavegador;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperAsignacion;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperInfoVehiculo;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperNotificacion;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperRegistro;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPropietario;
import ort.da.sistema_peajes.peaje.model.EventosSistema;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

@RestController
@RequestMapping("/tablero")
@Scope("session")

public class ControladorTableroPropietario implements Observador{

    private final ConexionNavegador conexionNavegador;
    private Propietario propietario;
    
    public ControladorTableroPropietario(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE();
    }

    //TODO que pasa cuando un propietario se va de su sesion y el boserver sigue asignado? Como manejamos la descuscripcion?


    @PostMapping("/informacion")
    public List<Respuesta> iniciarTablero(@SessionAttribute(name = "propietario", required = false) Propietario p){

        if(p == null){
            return Respuesta.lista(new Respuesta("accesoDenegado", "Debe volver a entrar"));
        }

        this.propietario = p;
        this.propietario.agregarObservador(this);

        return Respuesta.lista(
            propietario(),
            asignaciones(),
            vehiculos(),
            transitosRealizados(),
            notificaciones()
            );
    }

    @PostMapping("/borrarNotificaciones")
    public List<Respuesta> borrarNotificaciones(HttpSession sesionHttp){
        Propietario p = (Propietario) sesionHttp.getAttribute("propietario");
        String mensaje = "Notificaciones borradas con Éxito!";

        try{
            p.borrarNotificaciones();
        }catch(PropietarioException e){
            mensaje = "No hay notificaciones para borrar";
        }
        
        return Respuesta.lista(new Respuesta("borrarNotificaciones", mensaje));
    }

    @PostMapping("/nosVemos")
    public void salida(HttpSession sesionHttp){

        Propietario p = (Propietario) sesionHttp.getAttribute("propietario");

        if(p != null){
            sesionHttp.removeAttribute("propietario");
        }

        //return Respuesta.lista(new Respuesta("volver", "Debe volver a entrar"));
    }
    

    private Respuesta propietario(){
        return new Respuesta("propietario", MapperPropietario.toDTO(this.propietario));
    }

    //Lista de Asignaciones de Peajes del Propietario - Bonificacion, Puesto, FechaAsignada
    private Respuesta asignaciones(){
        return new Respuesta("asignaciones", MapperAsignacion.toDTOList(this.propietario.getAsignaciones()));
    }

    //Vehiculos del Propietario - Matricula, Modelo, Color, Transitos, MontoTotal
    private Respuesta vehiculos(){
        return new Respuesta("vehiculos", MapperInfoVehiculo.toDTOList(this.propietario.obtenerInfoVehiculos()));
    }

    //Transitos Realizados - Puesto, Matricula, Tarifa, MontoTarifa, Bonificacion, MontoBonificacion, MontoPagado, Fecha, Hora
    private Respuesta transitosRealizados(){
        return new Respuesta("transitosRealizados", MapperRegistro.toDTO(this.propietario.getRegistros()));
    }

    private Respuesta notificaciones(){
        return new Respuesta("notificaciones", MapperNotificacion.toDTOList(this.propietario.getNotificaciones()));
    }

    private Respuesta estado(){
        return new Respuesta("estado",this.propietario.getEstado());
    }

    private Respuesta saldo(){
        return new Respuesta("saldo", this.propietario.getSaldo());
    }

    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento.equals(EventosSistema.TRANSITO_REALIZADO)){
            conexionNavegador.enviarJSON(Respuesta.lista(saldo(), transitosRealizados()));
        }

        if(evento.equals(EventosSistema.BONO_ASIGNADO)){
            conexionNavegador.enviarJSON(Respuesta.lista(asignaciones()));
        }

        if(evento.equals(EventosSistema.ESTADO_NOTIFICACION)){
            conexionNavegador.enviarJSON(Respuesta.lista(estado(), notificaciones()));
        }

        if(evento.equals(EventosSistema.NOTIFICACION)){
            conexionNavegador.enviarJSON(Respuesta.lista(notificaciones()));
        }
    }
}
