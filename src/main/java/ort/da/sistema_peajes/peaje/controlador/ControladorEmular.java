package ort.da.sistema_peajes.peaje.controlador;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ort.da.sistema_peajes.Respuesta;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperPuesto;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperTarifa;
import ort.da.sistema_peajes.peaje.dto.mappers.MapperTransito;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.exceptions.VehiculoException;
import ort.da.sistema_peajes.peaje.service.Fachada;



@RestController
@RequestMapping("/emular")
public class ControladorEmular {

    @PostMapping("/inicio")
    public List<Respuesta> inicio(){
        return Respuesta.lista(puestos());
    }

    @PostMapping("/tarifas")
    public List<Respuesta> tarifasSegunPuesto(@RequestParam String nombre){
        return Respuesta.lista(tarifas(nombre));
    }

    @PostMapping("/transito")
    public List<Respuesta> emularTransito(@RequestParam int indicePuesto, @RequestParam String matricula, @RequestParam LocalDateTime fechaHora) throws SaldoException, EstadoException, VehiculoException, Exception{
        return Respuesta.lista(new Respuesta("transitoEmulado", MapperTransito.toDTO(Fachada.getInstancia().emularTransito(indicePuesto, matricula, fechaHora))));
    }

    private Respuesta puestos() {
        return new Respuesta("puestos", MapperPuesto.toDTOlist(Fachada.getInstancia().getPuestos()));
    }

    private Respuesta tarifas(String nombre){
        return new Respuesta("tarifas", MapperTarifa.toDTOlist(Fachada.getInstancia().obtenerTarifasPorPuestoNombre(nombre)));
    }
}
