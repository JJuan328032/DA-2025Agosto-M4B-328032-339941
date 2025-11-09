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
}

