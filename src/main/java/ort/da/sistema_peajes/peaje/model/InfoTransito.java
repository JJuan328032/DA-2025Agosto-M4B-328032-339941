package ort.da.sistema_peajes.peaje.model;

import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class InfoTransito {
    
    private Puesto puesto;
    private Vehiculo vehiculo;
    private String bono;
    private double montoTransito;

    public InfoTransito(Puesto puesto, Vehiculo vehiculo, String bono, double montoTransito) {
        this.puesto = puesto;
        this.vehiculo = vehiculo;
        this.bono = bono;
        this.montoTransito = montoTransito;
    }

    public Puesto getPuesto(){
        return this.puesto;
    }
    public Vehiculo getVehiculo() {
        return vehiculo;
    }
    public String getAsignacion() {
        return bono;
    }
    public double getMontoTransito() {
        return montoTransito;
    }
    public Propietario getPropietario() {
        return this.vehiculo.getPropietario();
    }

}
