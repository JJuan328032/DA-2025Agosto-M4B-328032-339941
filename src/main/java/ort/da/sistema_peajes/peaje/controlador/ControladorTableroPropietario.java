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

import observador.Observable;
import observador.Observador;
import ort.da.sistema_peajes.ConexionNavegador;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperAsignacion;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperInfoVehiculo;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperNotificacion;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperRegistro;
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

    //@SessionAttribute(name = "propietario") Propietario p
    @PostMapping("/informacion")
    public List<Respuesta> iniciarTablero(@SessionAttribute(name = "propietario", required = false) Propietario p){

        if(p == null){
            return Respuesta.lista(new Respuesta("accesoDenegado", "login_Propietario.html"));
        }

        this.propietario = p;
        this.propietario.agregarObservador(this);

        return Respuesta.lista(
            propietario(p),
            asignaciones(p),
            vehiculos(p),
            transitosRealizados(p),
            notificaciones(p)
            );
    }
    

    private Respuesta propietario(Propietario p){
        return new Respuesta("propietario", MapperPropietario.toDTO(p));
    }

    //Lista de Asignaciones de Peajes del Propietario - Bonificacion, Puesto, FechaAsignada
    private Respuesta asignaciones(Propietario p){
        return new Respuesta("asignaciones", MapperAsignacion.toDTOList(p.getAsignaciones()));
    }

    //Vehiculos del Propietario - Matricula, Modelo, Color, Transitos, MontoTotal
    private Respuesta vehiculos(Propietario p){
        return new Respuesta("vehiculos", MapperInfoVehiculo.toDTOList(p.obtenerInfoVehiculos()));
    }

    //Transitos Realizados - Puesto, Matricula, Tarifa, MontoTarifa, Bonificacion, MontoBonificacion, MontoPagado, Fecha, Hora
    private Respuesta transitosRealizados(Propietario p){
        return new Respuesta("transitosRealizados", MapperRegistro.toDTO(p.getRegistros()));
    }

    private Respuesta notificaciones(Propietario p){
        return new Respuesta("notificaciones", MapperNotificacion.toDTOList(p.getNotificaciones()));
    }

    //TODO separar en vista el espacio del saldo y estado. Si no se separan, siempre actualizo tres campos por cambiar uno

    @Override
    public void actualizar(Object evento, Observable origen) {
        if(evento.equals(EventosSistema.TRANSITO_REALIZADO) || evento.equals(EventosSistema.ESTADO)){
            conexionNavegador.enviarJSON(Respuesta.lista(propietario(this.propietario), transitosRealizados(this.propietario)));
        }

        if(evento.equals(EventosSistema.BONO_ASIGNADO)){
            conexionNavegador.enviarJSON(Respuesta.lista(asignaciones(this.propietario)));
        }

        if(evento.equals(EventosSistema.ESTADO_NOTIFICACION)){
            conexionNavegador.enviarJSON(Respuesta.lista(propietario(this.propietario), notificaciones(this.propietario)));
        }

        if(evento.equals(EventosSistema.NOTIFICACION)){
            conexionNavegador.enviarJSON(Respuesta.lista(notificaciones(this.propietario)));
        }
    }
}
