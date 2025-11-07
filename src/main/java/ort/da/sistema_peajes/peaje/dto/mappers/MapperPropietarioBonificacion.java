package ort.da.sistema_peajes.peaje.dto.mappers;

import ort.da.sistema_peajes.peaje.dto.PropietarioBonificacionDTO;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class MapperPropietarioBonificacion {

    public static PropietarioBonificacionDTO toDTO(Propietario p) {
        return new PropietarioBonificacionDTO(p.getNombreCompleto(), p.getEstado(), MapperAsignacion.toDTOList(p.getAsignaciones()));
    }
}
