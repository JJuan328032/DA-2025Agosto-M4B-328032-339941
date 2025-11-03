package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.TransitoDTO;
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.InfoTransito;
import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class MapperTransito {

    public static TransitoDTO toDTO(Propietario p, Vehiculo v, Asignacion a, int monto) {
        return new TransitoDTO(p.getNombreCompleto(), v.getCategoriaNombre(), a.getBonificacionNombre(), monto, p.getSaldo());
    }

    public static TransitoDTO toDTO(InfoTransito infoTransito) {
        return toDTO(infoTransito.getPropietario(), infoTransito.getVehiculo(), infoTransito.getAsignacion(), infoTransito.getMontoTransito());
    }

    public static ArrayList<TransitoDTO> toDTOList(ArrayList<InfoTransito> infoTransitos) {
        ArrayList<TransitoDTO> dtoList = new ArrayList<>();

        for (InfoTransito it : infoTransitos) {
            dtoList.add(toDTO(it.getPropietario(), it.getVehiculo(), it.getAsignacion(), it.getMontoTransito()));
        }

        return dtoList;
    }

}
