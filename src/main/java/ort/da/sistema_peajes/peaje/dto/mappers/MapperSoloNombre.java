package ort.da.sistema_peajes.peaje.dto.mappers;

import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.dto.NombreDTO;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;

public class MapperSoloNombre {

    public static NombreDTO toDTO(String nommbre){
        return new NombreDTO(nommbre);
    }

    public static ArrayList<NombreDTO> toDTOlistPuestos(ArrayList<Puesto> puestos){
        ArrayList<NombreDTO> nueva = new ArrayList<>();

        for(Puesto p : puestos) {
            nueva.add(toDTO(p.getNombre()));
        }
        

        return nueva;
    }

    public static ArrayList<NombreDTO> toDTOlistBonos(ArrayList<Bonificacion> bonos) {
        ArrayList<NombreDTO> nueva = new ArrayList<>();

        for(Bonificacion b : bonos) {
            nueva.add(toDTO(b.getNombre()));
        }
        

        return nueva;
    }
}
