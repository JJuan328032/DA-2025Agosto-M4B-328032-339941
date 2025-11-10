package ort.da.sistema_peajes.peaje.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AsignacionDTO {
	private String bonificacion;
    private String puesto;
	private String fecha;

	private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public AsignacionDTO(String bonificacion, String puesto, LocalDate fecha) {
		this.puesto = puesto;
		this.bonificacion = bonificacion;
		this.fecha = fecha.format(FORMATO);
	}

	public String getPuesto() {
		return puesto;
	}
	public String getBonificacion() {
		return bonificacion;
	}
	public String getFecha() {
		return fecha;
	}
}
