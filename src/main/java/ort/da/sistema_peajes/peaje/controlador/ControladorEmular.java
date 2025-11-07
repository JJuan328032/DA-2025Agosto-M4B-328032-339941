package ort.da.sistema_peajes.peaje.controlador;

import java.time.LocalDateTime;
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

import ort.da.sistema_peajes.ConexionNavegador;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperSoloNombre;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperTarifa;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperTransito;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.PuestoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.exceptions.VehiculoException;
import ort.da.sistema_peajes.peaje.service.Fachada;


@RestController
@RequestMapping("/emular")
@Scope("session")

public class ControladorEmular {

    private final ConexionNavegador conexionNavegador; 
    
    public ControladorEmular(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
    }

    @PostMapping("/inicio")
    public List<Respuesta> inicio(){
        //ValidarUsuario.validar(null, null);
        return Respuesta.lista(puestos());
    }

    @PostMapping("/tarifas")
    public List<Respuesta> tarifasSegunPuesto(@RequestParam String nombre) throws PuestoException{
        return Respuesta.lista(tarifas(nombre));
    }

    @PostMapping("/transito")
    public List<Respuesta> emularTransito(@RequestParam int indicePuesto, @RequestParam String matricula, @RequestParam LocalDateTime fechaHora) throws SaldoException, EstadoException, VehiculoException, PuestoException, Exception{
        //las exceptions son manejadas en sus respectivos objetos usando mensajes genericos o mas de un atributo

        try{
            return Respuesta.lista(new Respuesta("transitoEmulado", MapperTransito.toDTO(Fachada.getInstancia().emularTransito(indicePuesto, matricula, fechaHora))));
        }catch(VehiculoException e){
            return Respuesta.lista(new Respuesta("error", "No existe el Vehiculo con Matricula: " + e.getMessage()));
        }
    }

    private Respuesta puestos() {
        return new Respuesta("puestos", MapperSoloNombre.toDTOlistPuestos(Fachada.getInstancia().getPuestos()));
    }

    private Respuesta tarifas(String nombre) throws PuestoException{
        try{
            return new Respuesta("tarifas", MapperTarifa.toDTOlist(Fachada.getInstancia().obtenerTarifasPorPuestoNombre(nombre)));
        }catch(PuestoException e){
            throw new PuestoException("No se encontró un Puesto con nombre: " + e.getMessage());
        }
    }
}