package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.PropietarioBonificacionDTO;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class MapperPropietarioBonificacion {

    public static PropietarioBonificacionDTO toDTO(Propietario p) {
        return new PropietarioBonificacionDTO(p.getNombreCompleto(), p.getEstado());
    }

    public static ArrayList<PropietarioBonificacionDTO> toDTOList(ArrayList<Propietario> usuarios) {
        ArrayList<PropietarioBonificacionDTO> dtoList = new ArrayList<>();
        for (Propietario p : usuarios) {
            dtoList.add(toDTO(p));
        }

        return dtoList;
    }

}
