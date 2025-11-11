package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;
import java.util.List;

import ort.da.sistema_peajes.peaje.dto.EstadoDTO;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;

public class MapperEstado {

    public static List<EstadoDTO> toDTOlistEstados(List<EstadoPropietario> estados) {

        List<EstadoDTO> lista = new ArrayList<>();
        for (EstadoPropietario e : estados) {
            lista.add(new EstadoDTO(e.getNombre())); 
        }
        return lista;

    }
}
 