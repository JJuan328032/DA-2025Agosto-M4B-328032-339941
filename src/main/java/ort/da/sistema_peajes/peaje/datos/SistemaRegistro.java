package ort.da.sistema_peajes.peaje.datos;

import java.time.LocalDateTime;
import java.util.ArrayList;


import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.InfoTransito;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Registro;
import ort.da.sistema_peajes.peaje.model.Vehiculo;

public class SistemaRegistro {

	private ArrayList<Registro> registros;

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
		Registro r = new Registro(puesto, vehiculo, fechaHora, puesto.obtenerTarifaSegunCategoriaVehiculo(vehiculo));
		InfoTransito i = new InfoTransito(puesto, vehiculo, r.cobrar(), r.getMontoPagado());
		//pido al Propietario que me diga si tiene bonificacion y como sería el cobro al retornar su Asignacion

		//aplicar cobro con el Registro creado y la Asignacion recibida mandando validacionFrecuente por si es Frecuente

		this.agregarRegistro(r);

		//mando InfoTransito

		/*
		Habilitado: 
			Es el estado por defecto de los propietarios cuando se dan de alta en el sistema. El propietario tiene todas las funcionalidades habilitadas.


		Deshabilitado: 	
			El usuario no puede ingresar al sistema ni puede realizar tránsitos. Tampoco se le pueden asignar bonificaciones.
			NO GUARDA EL REGISTRO
			NO SE APLICAN BONOS

		Suspendido: 
			El usuario puede ingresar al sistema, pero no puede realizar tránsitos.
			NO GUARDA EL REGISTRO

		Penalizado: 
			El usuario puede ingresar al sistema, pero no se le registran notificaciones. 
			Puede realizar tránsitos, pero no aplican las bonificaciones que tenga asignadas. 
			NO SE APLICAN BONOS
		*/





		//BOOLEAN que sea true si es el segundo transito con ese vehiculo en el mismo dia

		//porque no cobrar el monto calculado desde acá para hacer las validaciones por si es Frecuente
		return i;
	}
}
