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
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPropietarioBonificacion;
import ort.da.sistema_peajes.peaje.exceptions.PropietarioException;
import ort.da.sistema_peajes.peaje.exceptions.PuestoException;
import ort.da.sistema_peajes.peaje.exceptions.BonificacionException;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.service.Fachada;

@RestController
@RequestMapping("/bonificaciones")
@Scope("session")

public class ControladorAsignarBonificaciones {

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

        return Respuesta.lista(
                new Respuesta("bonificacionesDefinidas", getBonificaciones()),
                new Respuesta("puestosDefinidos", getPuestos())
        );
    }

    // Buscar propietario por cédula
    @PostMapping("/propietario/buscar")
    public List<Respuesta> buscarPropietario(@RequestParam String cedula, HttpSession sesionHttp) throws LoginException, EstadoException, Exception {

        //System.out.println("Llegó al controlador con cédula: " + cedula);

        ValidarUsuario.validar(sesionHttp, "administrador");

        /*
        Administrador admin = (Administrador) sesionHttp.getAttribute("administrador");
        if (admin == null) {
            return Respuesta.lista(new Respuesta("Error", "No tiene permisos o la sesión expiró"));
        }
        */

        /*
        // Buscar el propietario desde la fachada
        Propietario encontrado = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (encontrado == null) {
            return Respuesta.lista(new Respuesta("Error", "No se encontró un propietario con esa cédula."));
        }

        System.out.println("Encontrado: " + encontrado.getNombreCompleto());

        // Obtener sus bonificaciones actuales
        List<Asignacion> asignaciones = Fachada.getInstancia().obtenerAsignacionesDePropietario(encontrado);
        */

        //TODO
        //Organizar para que MapperPropietarioBonificacion ya tome las asignaciones y las guarde para ser mostradas


        /*
        return Respuesta.lista(
                new Respuesta("propietario", MapperPropietarioBonificacion.toDTO(encontrado)),
                new Respuesta("bonificaciones", asignaciones)
        );
        */

        //TODO
        //Por los cambios de la Respuesta hay que cambiar como se recibe la informacion en la vista

        return Respuesta.lista(new Respuesta("propietarioBonificacion", MapperPropietarioBonificacion.toDTO(Fachada.getInstancia().buscarPropietarioPorCedula(cedula))));
    } 

    //  Asignar bonificaciones a un propietario
    
    @PostMapping("/propietario/asignar")
    public Respuesta asignarBonificaciones(
            @RequestParam String cedula,
            @RequestParam String bonificacion,
            @RequestParam String puesto,
            HttpSession sesionHttp) throws PropietarioException, Exception {

                /* 
                System.out.println("📩 [CONTROLADOR] Datos recibidos del frontend:");
                System.out.println("   Cedula: " + cedula);
                System.out.println("   Bonificacion: " + bonificacion);
                System.out.println("   Puesto: " + puesto);
                */

        /*
        Administrador admin = (Administrador) sesionHttp.getAttribute("administrador");
        if (admin == null) {
            return new Respuesta("Error", "No tiene permisos o la sesión expiró");
        }
        */

        


        /*
        // Buscar propietario
        Propietario propietario = Fachada.getInstancia().buscarPropietarioPorCedula(cedula);
        if (propietario == null) {
            return new Respuesta("Error", "No se encontró el propietario indicado");
        }

        //sería mejor recibir la fecha desde el front o generarla a partir del momento del registro?


        // Asignar bonificaciones mediante la lógica de Fachada
        boolean exito = Fachada.getInstancia().asignarBonificaciones(propietario, bonificacion, puesto);
        if (!exito) {
            return new Respuesta("Error", "Hubo un problema al asignar las bonificaciones");
        }
        */

        String mensaje = "";

        try{
            ValidarUsuario.validar(sesionHttp, "administrador");
            Fachada.getInstancia().asignarBonificaciones(cedula, bonificacion, puesto);
        }catch(PropietarioException e){
            mensaje = "No se pudo encontrar un Propietario con nombre: " + e.getMessage();
        }catch(BonificacionException e){
            mensaje = "No se pudo encontrar una Bonificacion con nombre: " + e.getMessage();
        }catch(PuestoException e){
            mensaje = "No se pudo encontrar un Puesto con nombre: " + e.getMessage();
        }

        return new Respuesta("mensaje", mensaje); 
    }



    private List<Respuesta> getBonificaciones(){
        return Respuesta.lista(new Respuesta("bonificacionesDefinidas", Fachada.getInstancia().obtenerBonificaciones()));
    }

    private List<Respuesta> getPuestos(){
        return Respuesta.lista(new Respuesta("puestosDefinidos", Fachada.getInstancia().obtenerPuestos()));
    }
}
