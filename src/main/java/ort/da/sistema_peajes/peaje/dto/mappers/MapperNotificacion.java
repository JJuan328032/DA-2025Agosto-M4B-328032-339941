package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.NotificacionDTO;
import ort.da.sistema_peajes.peaje.model.Notificacion;

public class MapperNotificacion {
    public static ArrayList<NotificacionDTO> toDTOList(ArrayList<Notificacion> notificaciones){
        ArrayList<NotificacionDTO> lista = new ArrayList<>();
        for(Notificacion n : notificaciones) lista.add(new NotificacionDTO(n.getFecha(), n.getMensaje()));
        return lista;
    }
}
