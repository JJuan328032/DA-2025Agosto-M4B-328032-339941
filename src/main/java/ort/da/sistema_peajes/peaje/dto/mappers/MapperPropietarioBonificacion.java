package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.AsignacionDTO;
import ort.da.sistema_peajes.peaje.dto.PropietarioBonificacionDTO;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class MapperPropietarioBonificacion {

    public static PropietarioBonificacionDTO toDTO(Propietario p) {
        ArrayList<AsignacionDTO> lista = MapperAsignacion.toDTOList(p.getAsignaciones());
        return new PropietarioBonificacionDTO(p.getNombreCompleto(), p.getEstado(), lista);
    }
}
