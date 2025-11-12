package ort.da.sistema_peajes.peaje.model;

import java.time.LocalDateTime;

public class Notificacion {
    private LocalDateTime fecha;
    private String mensaje;
    

    public Notificacion(String mensaje) {
        this.fecha = LocalDateTime.now();
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() { return fecha; }
    public String getMensaje() { return mensaje; }

    public static Notificacion notificarSaldoBajo(int saldo){
        return new Notificacion("Tu saldo actual es de $" + saldo + ". Te recomendamos hacer una recarga");
    }

    public static Notificacion notificarTransito(String puesto, String matricula){
        return new Notificacion("Pasaste por el puesto " + puesto + " con el vehiculo " + matricula);
    }

    public static Notificacion notificarBonificacion(String bonificacion, String puesto) {
        return new Notificacion("Se le ha asignado la bonificacion " + bonificacion + " en el puesto " + puesto);
    }

    public static Notificacion notificarEstado(String estado) {
        return new Notificacion("Se ha cambiado tu estado en el sistema. Tu estado actual es " + estado);
    }
}

