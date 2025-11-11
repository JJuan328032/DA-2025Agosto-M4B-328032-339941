package ort.da.sistema_peajes.peaje.model.Estados;
public class EstadoFactory {

    public static EstadoPropietario crear(String tipo) {
        switch (tipo.toLowerCase()) {
            case "habilitado":
                return new Habilitado(null);
            case "deshabilitado":
                return new Deshabilitado(null);
            case "suspendido":
                return new Suspendido(null);
            case "penalizado":
                return new Penalizado(null);
            default:
                throw new IllegalArgumentException("Tipo de estado desconocido: " + tipo);
        }
    }
}
