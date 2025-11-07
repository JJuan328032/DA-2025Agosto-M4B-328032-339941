package ort.da.sistema_peajes.peaje.controlador;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.servlet.http.HttpSession;
import ort.da.sistema_peajes.ConexionNavegador;
import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperAsignacion;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPropietarioBonificacion;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperSoloNombre;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.exceptions.PuestoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;
import ort.da.sistema_peajes.peaje.exceptions.AsignacionException;
import ort.da.sistema_peajes.peaje.exceptions.BonificacionException;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.service.Fachada;

@RestController
@RequestMapping("/bonificaciones")
@Scope("session")

public class ControladorAsignarBonificaciones {

    private Propietario propietario;
    private final ConexionNavegador conexionNavegador; 
    
    public ControladorAsignarBonificaciones(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }
    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        return conexionNavegador.getConexionSSE(); 
       
    }


    // Obtener bonificaciones y puestos para llenar los dropdowns
    @PostMapping("/opciones")
    public List<Respuesta> obtenerOpciones(HttpSession sesionHttp) throws Exception{

        ValidarUsuario.validar(sesionHttp, "administrador");

        return Respuesta.lista(bonificaciones(), puestos());
    }

    // Buscar propietario por cédula
    @PostMapping("/propietario/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula, HttpSession sesionHttp) throws LoginException, EstadoException, Exception {

        ValidarUsuario.validar(sesionHttp, "administrador");
        
        try{
            this.propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
            return Respuesta.lista(formatoPropietario());
        }catch(PropietarioException e){
            return Respuesta.lista(new Respuesta("error", "No se pudo encontrar un propietario con cedula: " + e.getMessage()));
        }
    }


    //  Asignar bonificaciones a un propietario
    @PostMapping("/propietario/asignar")
    public List<Respuesta> asignarBonificaciones(
            @RequestParam String cedula,
            @RequestParam String bonificacion,
            @RequestParam String puesto,
            HttpSession sesionHttp) throws PropietarioException, Exception {

        String estado = "mal";
        String mensaje = "Bonificación: " + bonificacion + " asignada con Éxito en el puesto: " + puesto;

        try{
            ValidarUsuario.validar(sesionHttp, "administrador");
            Fachada.getInstancia().asignarBonificaciones(cedula, bonificacion, puesto);
            estado = "ok";
        }catch(PropietarioException e){
            mensaje = "No se pudo encontrar un Propietario con nombre: " + e.getMessage();
        }catch(BonificacionException e){
            mensaje = "No se pudo encontrar una Bonificacion con nombre: " + e.getMessage();
        }catch(PuestoException e){
            mensaje = "No se pudo encontrar un Puesto con nombre: " + e.getMessage();
        }catch(AsignacionException e){
            mensaje = "Ya existe un bono " + bonificacion + " en el puesto " + puesto + " asociada a ese propietario. Vuelva a Intentar con otros valores";
        }

        return Respuesta.lista(new Respuesta(estado, mensaje), bonosPropietario()); 
    }



    private Respuesta bonificaciones(){
        return new Respuesta("bonificacionesDefinidas", MapperSoloNombre.toDTOlistBonos(Fachada.getInstancia().obtenerBonificaciones()));
    }

    private Respuesta puestos() {
        return new Respuesta("puestosDefinidos", MapperSoloNombre.toDTOlistPuestos(Fachada.getInstancia().getPuestos()));
    }

    private Respuesta formatoPropietario(){
        return new Respuesta("propietarioBonificacion", MapperPropietarioBonificacion.toDTO(this.propietario));
    }

    private Respuesta bonosPropietario(){
        return new Respuesta("bonificaciones", MapperAsignacion.toDTOList((this.propietario.getAsignaciones())));
    }
}
