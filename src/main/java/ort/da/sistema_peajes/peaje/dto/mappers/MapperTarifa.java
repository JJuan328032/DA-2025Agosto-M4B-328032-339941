package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.TarifaDTO;
import ort.da.sistema_peajes.peaje.model.Tarifa;

public class MapperTarifa {

    public static TarifaDTO toDTO(Tarifa t){
        return new TarifaDTO(t.getCategoriaNombre(), t.getMonto());
    }

    public static ArrayList<TarifaDTO> toDTOlist(ArrayList<Tarifa> lista){
        ArrayList<TarifaDTO> nueva = new ArrayList<>();

        for(Tarifa t : lista) nueva.add(toDTO(t));

        return nueva;
    }
}
