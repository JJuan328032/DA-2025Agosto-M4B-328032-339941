package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.AsignacionDTO;
import ort.da.sistema_peajes.peaje.model.Asignacion;

public class MapperAsignacion {

    public static AsignacionDTO toDTO(Asignacion asignacion) {
        return new AsignacionDTO(asignacion.getBonificacionNombre(), asignacion.getPuestoNombre(), asignacion.getFecha());
    }

    public static ArrayList<AsignacionDTO> toDTOList(ArrayList<Asignacion> asignaciones) {
        ArrayList<AsignacionDTO> dtoList = new ArrayList<>();
        
        for (Asignacion a : asignaciones) {
            dtoList.add(toDTO(a));
        }

        return dtoList;
    }


}
