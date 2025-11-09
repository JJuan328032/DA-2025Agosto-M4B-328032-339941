package ort.da.sistema_peajes.peaje.dto;

import java.time.LocalDateTime;

public class NotificacionDTO {
    private LocalDateTime fecha;
    private String mensaje;

    public NotificacionDTO(LocalDateTime fecha, String mensaje){
        this.fecha = fecha;
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha(){ return this.fecha;}
    public String getMensaje(){ return this.mensaje;}
}
