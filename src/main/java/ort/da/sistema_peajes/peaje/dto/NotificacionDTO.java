package ort.da.sistema_peajes.peaje.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificacionDTO {
    private String fecha;
    private String mensaje;

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NotificacionDTO(LocalDateTime fecha, String mensaje){
        this.fecha = fecha.format(FORMATO);
        this.mensaje = mensaje;
    }

    public String getFecha(){ return this.fecha;}
    public String getMensaje(){ return this.mensaje;}
}
