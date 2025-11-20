package ort.da.sistema_peajes.peaje.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Vehiculo {

	private Propietario propietario;
	private String matricula;
	private String modelo;
	private String color;
	private CategoriaVehiculos categoria;

	private ArrayList<Registro> registros;

	public Vehiculo(String matricula, String modelo, String color, String nombreCategoria) {
		this.propietario = null;
		this.matricula = matricula;
		this.modelo = modelo;
		this.color = color;

		this.registros = new ArrayList<>();
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

	public ArrayList<Registro> getRegistros() {
        return this.registros;
    }

	public void agregarRegistro(Registro r) {
        this.registros.add(r);

        this.propietario.avisar(EventosSistema.TRANSITO_REALIZADO);
    }

	//cuantos registros existen de determinado vehiculo, guardar cantidad y monto total
	public InfoVehiculo obtenerRegistrosPorVehiculo() {
		int contador = 0;
		double montoTotal = 0.0;

		for (Registro r : this.registros) {
			contador++;
			montoTotal += r.getMontoPagado();
		}
        
		return new InfoVehiculo(this, contador, montoTotal);
	}

	public boolean esSegundoTransitoDelDia(Puesto puesto, LocalDateTime fecha) {
		int cont = 0;

		for(int i = 0; i < this.registros.size() && cont < 1; i++){
			if(this.registros.get(i).validarMismoDia(puesto, this, fecha)) cont++;
		}

		return cont == 1;
	}




    public boolean igualPatente(String matricula) {
        return this.matricula.equalsIgnoreCase(matricula);
    }

	public String toString(){ return "MAtricula: " + this.matricula + " Categoria: " + this.getCategoriaNombre();}
}
