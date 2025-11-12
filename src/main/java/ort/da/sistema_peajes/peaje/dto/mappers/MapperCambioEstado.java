package ort.da.sistema_peajes.peaje.dto.mappers;

import ort.da.sistema_peajes.peaje.dto.CambioEstadoDTO;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class MapperCambioEstado {

    public static CambioEstadoDTO toDTO(Propietario propietario) {
        return new CambioEstadoDTO(
            propietario.getCedula(),
            propietario.getEstado()
        );
    }

    public static Propietario fromDTO(CambioEstadoDTO dto) {
        // Esto no crea el propietario completo, solo devuelve un placeholder
        Propietario p = new Propietario("","", "", dto.getCedula());
        return p;
    }
}
