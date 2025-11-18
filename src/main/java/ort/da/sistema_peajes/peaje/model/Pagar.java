package ort.da.sistema_peajes.peaje.model;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Pagar {

    protected static void realizarPagoSimple(Propietario propietario, Registro registro) throws EstadoException, SaldoException{

        if(propietario.validarEstado()){
            Puesto puesto = registro.getPuesto();
            Vehiculo vehiculo = registro.getVehiculo();
            int montoTarifa = registro.getMontoTarifa();

            Asignacion asignacion = propietario.buscarAsignacionSegunPuesto(puesto);

            if(asignacion != null) {

                boolean segundoTransito =  false;

                //TODO esta validacion es relevante?
                //TODO se puede mejorar esta secuencia?

                if(asignacion.mismoBono("frecuente")) segundoTransito =  propietario.esSegundoTransitoDelDia(puesto, vehiculo, registro.getFecha());

                //boolean aux = this.esSegundoTransitoDelDia(puesto, vehiculo, registro.getFecha());
                //System.out.println("segundoTransitoDia: " + aux);

                double montoBonificado = asignacion.calcularMontoBonificado(montoTarifa, segundoTransito);

                registro.setMontoNombreBono(montoBonificado, asignacion.getBonificacionNombre());
            }

            completarRegistro(propietario, registro);
            notificarTransito(propietario, puesto.getNombre(), vehiculo.getMatricula());
        }
    }

    private static void completarRegistro(Propietario propietario, Registro registro) throws SaldoException{
        //se usa registro.calcularPrecioFinal() por si existe un montoBonificado. Así se se puede usar para ambos casos
        descontarTransito(propietario, registro.calcularMontoPagar());
        registro.setMontoPagado();

        propietario.agregarRegistro(registro);
    }

    private static void descontarTransito(Propietario propietario, double monto) throws SaldoException{

        int saldo = propietario.getSaldo();

        //System.out.println("Resta descontarTransito: " + (this.saldo - monto));
        if(saldo - monto < 0) throw new SaldoException("Saldo Actual: " + saldo + "\n Saldo insuficiente para cobrar el total: " + monto);

        //funciona si es Exonerado y el saldo es cero
        propietario.restarMonto(monto);
    }

    private static void notificarTransito(Propietario propietario, String puesto, String matricula) {
        propietario.agregarNotificacion_PAGO_REALIZADO(Notificacion.notificarTransito(puesto, matricula));
    }
}
