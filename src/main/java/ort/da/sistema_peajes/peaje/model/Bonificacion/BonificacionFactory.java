package ort.da.sistema_peajes.peaje.model.Bonificacion;

public class BonificacionFactory {

    public static Bonificacion crear(String tipo) {
        switch (tipo.toLowerCase()) {
            case "exonerado":
                return new Exonerado();
            case "frecuente":
                return new Frecuente();
            case "trabajador":
                return new Trabajador();
            default:
                return new SinBono();
        }
    }
}
