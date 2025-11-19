package ort.da.sistema_peajes.peaje.datos;

import java.time.LocalDateTime;
import java.util.ArrayList;


import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.InfoTransito;
import ort.da.sistema_peajes.peaje.model.Pagar;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Registro;
import ort.da.sistema_peajes.peaje.model.Tarifa;
import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class SistemaRegistro {

	private ArrayList<Registro> registros;
	private Pagar servicioPago = new Pagar();


	public SistemaRegistro() {
		this.registros = new ArrayList<>();
	}

	public void agregarRegistro(Registro registro) {
		this.registros.add(registro);
	}

	public ArrayList<Registro> getRegistros() {
		return this.registros;
	}

	public InfoTransito realizarTransito(Puesto puesto, Vehiculo vehiculo, LocalDateTime fechaHora) throws SaldoException, EstadoException, Exception{
		Tarifa tarifas = puesto.obtenerTarifaSegunCategoriaVehiculo(vehiculo);
		Registro r = new Registro(puesto, vehiculo, fechaHora, tarifas);

		Propietario propietario = vehiculo.getPropietario();
		//se cobra antes para validar estado de Propietario
		this.servicioPago.realizarPagoSimple(propietario, r);
		
		this.agregarRegistro(r);

		return new InfoTransito(puesto, vehiculo, r.getBonificacion(), r.getMontoPagado());
	}
}
