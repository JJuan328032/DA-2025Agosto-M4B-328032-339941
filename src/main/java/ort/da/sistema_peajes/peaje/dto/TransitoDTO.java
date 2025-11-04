package ort.da.sistema_peajes.peaje.dto;

public class TransitoDTO {

    private String propietario;
    private String categoria;
    private String bonificacion;
    private double costoTransito;
    private int saldoLuegoTransito;

    public TransitoDTO(String propietario, String categoria, String bonificacion, double costoTransito, int saldoLuegoTransito) {
        this.propietario = propietario;
        this.categoria = categoria;

        if(bonificacion == null) this.bonificacion = "Sin Bonificación";
        else this.bonificacion = bonificacion;

        this.costoTransito = costoTransito;
        this.saldoLuegoTransito = saldoLuegoTransito;
    }

    public String getPropietario() {
        return propietario;
    }
    public String getCategoria() {
        return categoria;
    }
    public String getBonificacion() {
        return bonificacion;
    }
    public double getCostoTransito() {
        return costoTransito;
    }
    public int getSaldoLuegoTransito() {
        return saldoLuegoTransito;
    }
}
