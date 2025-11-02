package ort.da.sistema_peajes.peaje.model;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Vehiculo {

	private Propietario propietario;
	private String matricula;
	private String modelo;
	private String color;
	private CategoriaVehiculos categoria;

	public Vehiculo(String matricula, String modelo, String color, String nombreCategoria) {
		this.propietario = null;
		this.matricula = matricula;
		this.modelo = modelo;
		this.color = color;
		this.categoria = new CategoriaVehiculos(nombreCategoria);
	}

	public Propietario getPropietario() {
		return propietario;
	}

	public void setPropietario(Propietario propietario) {
		this.propietario = propietario;
	}

	public String getMatricula() {
		return matricula;
	}

	public String getModelo() {
		return modelo;
	}

	public String getColor() {
		return color;
	}

	public CategoriaVehiculos getCategoria() {
		return categoria;
	}

	public String getCategoriaNombre() {
		return categoria.getNombre();
	}

    public boolean igualPatente(String matricula) {
        return this.matricula.equalsIgnoreCase(matricula);
    }

    public Asignacion realizarPago(Registro registro) throws SaldoException, EstadoException{
        return this.getPropietario().realizarPago(registro);
    }


	public String toString(){ return "MAtricula: " + this.matricula + " Categoria: " + this.getCategoriaNombre();}
}
