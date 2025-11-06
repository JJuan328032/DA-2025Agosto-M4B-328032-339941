package ort.da.sistema_peajes.peaje.dto;

import java.time.LocalDate;


public class AsignacionDTO {
	private String bonificacion;
    private String puesto;
	private LocalDate fecha;

	public AsignacionDTO(String bonificacion, String puesto, LocalDate fecha) {
		this.puesto = puesto;
		this.bonificacion = bonificacion;
		this.fecha = fecha;
	}

	public String getPuesto() {
		return puesto;
	}
	public String getBonificacion() {
		return bonificacion;
	}
	public LocalDate getFecha() {
		return fecha;
	}
}
