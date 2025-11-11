package ort.da.sistema_peajes.peaje.model;

import java.time.LocalDateTime;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;

public class Registro {
    private Puesto puesto;
    private Vehiculo vehiculo;
    private LocalDateTime fecha;
    
    private String tarifa;
    private int montoTarifa;

    private double montoBonificado;
    private String bonificacion;

    private double montoPagado;

    public Registro(Puesto puesto, Vehiculo vehiculo, LocalDateTime fecha, Tarifa tarifa) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.fecha = fecha;
        this.tarifa = tarifa.getTipo();
        this.montoTarifa = tarifa.getMonto();
        this.montoBonificado = 0;
        this.bonificacion = "Sin Bonificacion";
    }


    public Puesto getPuesto() {
        return puesto;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public double getMontoBonificado() {
        return montoBonificado;
    }

    public void setBonificacion(String b){
        this.bonificacion = b;
    }

    public void setMontoBonificado(double montoBonificado) {
        this.montoBonificado = montoBonificado;
    }

    public String getBonificacion() {
        return bonificacion;
    }

    public double getMontoPagado() {
        return montoPagado;
    }

    public String getMatricula() {
        return this.vehiculo.getMatricula();
    }

    public String getTipoTarifa() {
        return this.tarifa;
    }

    public int getMontoTarifa() {
        return this.montoTarifa;
    }


    public double getMontoBonificacion() {
        return this.montoBonificado;
    }

    public String getPuestoNombre() {
        return this.puesto.getNombre();
    }

    public void setMontoNombreBono(double monto, String nombre){
        this.setBonificacion(nombre);
        this.setMontoBonificado(monto);
    }

    //usado en SeedData
    public void setMontoPagado() {
        this.montoPagado = (double) (this.montoTarifa - this.montoBonificado);
    }

    public String toString() {
        return "Registro [puesto=" + puesto.getNombre() + ", vehiculo=" + vehiculo.getMatricula() + ", fecha=" + fecha.toLocalDate()
                + ", hora=" + fecha.toLocalTime() + ", tarifa=" + tarifa + ", montoTarifa=" + montoTarifa + ", montoBonificado="
                + montoBonificado + ", bonificacion=" + (bonificacion != null ? bonificacion : "N/A") 
                + ", montoPagado=" + montoPagado + "]";
    }


    public void cobrar() throws SaldoException, EstadoException{
        this.getVehiculo().realizarPago(this);
    }


    public double calcularMontoPagar(){
        this.montoPagado = (double) (this.montoTarifa - this.montoBonificado);
        //System.out.println("MontoPagado en Registro: " + this.montoPagado);
        return this.montoPagado;
    }

    public boolean validarMismoDia(Puesto puesto2, Vehiculo vehiculo2, LocalDateTime fecha2) {
        return this.puesto.equals(puesto2) && this.vehiculo.equals(vehiculo2) && this.fecha.equals(fecha2);
    }

}
