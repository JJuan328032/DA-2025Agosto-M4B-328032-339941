package ort.da.sistema_peajes.peaje.model.Bonificacion;

public class SinBono extends Bonificacion{

    public SinBono() {
        super("Sin Bonificación", 0);
    }

    @Override
    public double calcular(int monto, boolean validar) {
        return 0;
    }

}
