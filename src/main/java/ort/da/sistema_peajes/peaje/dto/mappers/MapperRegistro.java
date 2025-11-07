package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.RegistroDTO;
import ort.da.sistema_peajes.peaje.model.Registro;

public class MapperRegistro {

    public static RegistroDTO toDTO(Registro r) {
        return new RegistroDTO(
            r.getPuestoNombre(),
            r.getMatricula(),
            r.getTipoTarifa(),
            r.getMontoTarifa(),
            r.getBonificacion(),
            r.getMontoBonificacion(),
            r.getMontoPagado(),
            r.getFecha()
        );
    }

    public static ArrayList<RegistroDTO> toDTO(ArrayList<Registro> registros) {
        ArrayList<RegistroDTO> listaDTO = new ArrayList<>();

        for(Registro r : registros) {
            listaDTO.add(toDTO(r));
        }

        return listaDTO;
    }
}
