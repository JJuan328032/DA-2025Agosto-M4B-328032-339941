package ort.da.sistema_peajes.peaje.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NotificacionDTO {
    private String fecha;
    private String mensaje;

    private static final DateTimeFormatter FORMATO_COMPLETO =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public NotificacionDTO(LocalDateTime fecha, String mensaje){
        this.fecha = fecha.format(FORMATO_COMPLETO);
        this.mensaje = mensaje;
    }

    public String getFecha(){ return this.fecha;}
    public String getMensaje(){ return this.mensaje;}
}
