package ort.da.sistema_peajes.peaje.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroDTO {
    private String puesto;
    private String matricula;
    private String tarifa;
    private int montoTarifa;
    private String bonificacion;
    private double montoBonificacion;
    private double montoPagado;
    private String fecha;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public RegistroDTO(String puesto, String matricula, String tarifa, int montoTarifa, String bonificacion,
            double montoBonificacion, double montoPagado, LocalDateTime fecha) {
        this.puesto = puesto;
        this.matricula = matricula;
        this.tarifa = tarifa;
        this.montoTarifa = montoTarifa;
        this.bonificacion = bonificacion;
        this.montoBonificacion = montoBonificacion;
        this.montoPagado = montoPagado;
        this.fecha = fecha.format(FORMATO);
    }

    public String getPuesto() {
        return puesto;
    }
    public String getMatricula() {
        return matricula;
    }
    public String getTarifa() {
        return tarifa;
    }
    public int getMontoTarifa() {
        return montoTarifa;
    }
    public String getBonificacion() {
        return bonificacion;
    }
    public double getMontoBonificacion() {
        return montoBonificacion;
    }
    public double getMontoPagado() {
        return montoPagado;
    }
    public String getFecha() {
        return fecha;
    }
    
}
