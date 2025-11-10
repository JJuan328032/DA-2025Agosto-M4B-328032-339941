package ort.da.sistema_peajes.peaje.model.Usuarios;

import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Bonificacion.Bonificacion;
import ort.da.sistema_peajes.peaje.model.Estados.EstadoPropietario;
import ort.da.sistema_peajes.peaje.model.Estados.Habilitado;
import ort.da.sistema_peajes.peaje.model.Registro;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import ort.da.sistema_peajes.peaje.exceptions.AsignacionException;
import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.EventosSistema;
import ort.da.sistema_peajes.peaje.model.InfoVehiculo;
import ort.da.sistema_peajes.peaje.model.Notificacion;
import ort.da.sistema_peajes.peaje.model.Puesto;

public class Propietario extends Usuario {

	private int saldo;
	private int saldoMinimo;

	private ArrayList<Vehiculo> vehiculos;
	private ArrayList<Registro> registros;
	private ArrayList<Asignacion> asignaciones;


    private ArrayList<Notificacion> notificaciones;

	private EstadoPropietario estadoPropietario;


	public Propietario(String usuario, String password, String nombreCompleto, String cedula) {
        super(usuario, password, nombreCompleto, cedula);
        this.vehiculos = new ArrayList<>();
        this.registros = new ArrayList<>();
        this.asignaciones = new ArrayList<>();
        this.notificaciones = new ArrayList<>();
        this.saldo = 0;
        this.saldoMinimo = 1000;
        this.estadoPropietario = new Habilitado(this);
    }


    //usado para mostrar el tipo de estado en tablero CU Tablero de control del propietario
    public String getEstado() {
        return this.estadoPropietario.toString();
    }

    public int getSaldo() {
        return this.saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public int getSaldoMinimo() {
        return this.saldoMinimo;
    }

    public void setSaldoMinimo(int saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public ArrayList<Vehiculo> getVehiculos() {
        return this.vehiculos;
    }

    public ArrayList<Registro> getRegistros() {
        return this.registros;
    }

    public ArrayList<Asignacion> getAsignaciones() {
        return this.asignaciones;
    }

    public ArrayList<Notificacion> getNotificaciones() {
        return this.notificaciones;
    }

    public EstadoPropietario getEstadoPropietario() {
        return this.estadoPropietario;
    }

    public void setEstadoPropietario(EstadoPropietario estadoPropietario) {
        this.estadoPropietario = estadoPropietario;
    }

    public void agregarVehiculo(Vehiculo v) {
        this.vehiculos.add(v);
    }

    public void agregarRegistro(Registro r) {
        this.registros.add(r);
    }

    public void agregarAsignacion(Asignacion a) {
        this.asignaciones.add(a);
    }

    public void agregarAsignacion(Bonificacion obtenerBonificacionByNombre, Puesto obtenerPuestoPorNombre) throws AsignacionException{
        Asignacion nueva = new Asignacion(obtenerPuestoPorNombre, obtenerBonificacionByNombre, LocalDate.now());

        existeAsignacion(nueva);
        this.asignaciones.add(nueva);

        System.out.println("Mandando avisar(EventosSistema.BONO_ASIGNADO)");
        this.avisar(EventosSistema.BONO_ASIGNADO);
    }

    public void agregarNotificacion(String mensaje) {
        notificaciones.add(new Notificacion(mensaje));
    }


	@Override
	public void Validar() throws EstadoException, LoginException {
		this.estadoPropietario.puedeEntrar();
	}




    public ArrayList<InfoVehiculo> obtenerInfoVehiculos(){
        ArrayList<InfoVehiculo> lista = new ArrayList<>();

        for (Vehiculo v : this.vehiculos) {
            lista.add(obtenerRegistrosPorVehiculo(v));
        }

        return lista;
    }

    //cuantos registros existen de determinado vehiculo, guardar cantidad y monto total
	private InfoVehiculo obtenerRegistrosPorVehiculo(Vehiculo v) {
		int contador = 0;
		double montoTotal = 0.0;

		for (Registro r : this.registros) {
			if (r.getVehiculo().equals(v)) {
				contador++;
				montoTotal += r.getMontoPagado();
			}
		}
        
		return new InfoVehiculo(v, contador, montoTotal);
	}


    public void realizarPago(Registro registro) throws SaldoException, EstadoException{

        //nueva clase Pagar
        //conoce a Registro, Vehiculo, Propietario
        //maneja registro de quienes pagaron y c centraliza la logica de pago

        if(validarEstado()){
            //validar si tengo una bonificacion en el puesto
            Puesto puesto = registro.getPuesto();
            Vehiculo vehiculo = registro.getVehiculo();
            int montoTarifa = registro.getMontoTarifa();


            Asignacion a = this.buscarAsignacionSegunPuesto(puesto);

            //si la tengo, seteo el montoBonificado previamente inicializado en cero
            //Y si dejamos propietario en Bonificacion y si es frecuente accede directamente a esSegundoTransitoDelDia()?
            if(a != null) {

                boolean segundoTransito =  false;

                boolean aux = this.esSegundoTransitoDelDia(puesto, vehiculo, registro.getFecha());
                System.out.println("segundoTransitoDia: " + aux);

                if(a.getBonificacionNombre() == "frecuente") segundoTransito =  aux;

                double montoBonificado = a.calcularMontoBonificado(montoTarifa, segundoTransito);
                System.out.println("montoBonificado: " + montoBonificado);


                registro.setMontoNombreBono(montoBonificado, a.getBonificacionNombre());
            }
        }

        completarRegistro(registro);
        this.avisar(EventosSistema.TRANSITO_REALIZADO);
    }

    private void completarRegistro(Registro registro) throws SaldoException{
        //se usa registro.calcularPrecioFinal() por si existe un montoBonificado. Así se se puede usar para ambos casos
        descontarTransito(registro.calcularMontoPagar());
        registro.setMontoPagado();

        this.agregarRegistro(registro);
    }

    private void descontarTransito(double monto) throws SaldoException{
        //TODO: queda negativo por la resta? si saldo es cero y monto no
        System.out.println("Resta descontarTransito: " + (this.saldo - monto));
        if(this.saldo - monto < 0) throw new SaldoException("Saldo insuficiente: " + this.saldo + " para cobrar el total: " + monto);

        //funciona si es Exonerado y el saldo es cero
        this.saldo -= monto;
    }

    private boolean validarEstado() throws EstadoException{
        //si no puede, manda exception y corta ejecucion
        this.estadoPropietario.puedeTransitar();

        //si no puede recibir bonificaciones, registro se encarga
        return this.estadoPropietario.bonificable();
    }

    private Asignacion buscarAsignacionSegunPuesto(Puesto puesto) {
        for(Asignacion a : this.asignaciones){
            if(a.equals(puesto)) return a;
        }

        return null;
    }

    private void existeAsignacion(Asignacion buscada) throws AsignacionException{
        for(Asignacion a : this.asignaciones) if(a.equals(buscada)) throw new AsignacionException("");
    }

    public boolean esSegundoTransitoDelDia(Puesto puesto, Vehiculo vehiculo, LocalDateTime fecha) {
		int cont = 0;

		for(int i = 0; i < this.registros.size() && cont < 2; i++){
			if(this.registros.get(i).validarMismoDia(puesto, vehiculo, fecha)) cont++;
		}

		return cont == 2;
	}

}
