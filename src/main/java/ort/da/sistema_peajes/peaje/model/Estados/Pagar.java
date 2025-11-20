package ort.da.sistema_peajes.peaje.model.Estados;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Asignacion;
import ort.da.sistema_peajes.peaje.model.Notificacion;
import ort.da.sistema_peajes.peaje.model.Puesto;
import ort.da.sistema_peajes.peaje.model.Registro;
import ort.da.sistema_peajes.peaje.model.Vehiculo;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Pagar {


    public void realizarPagoSimple(Propietario propietario, Registro registro) throws EstadoException, SaldoException {
        propietario.getEstadoPropietario().procesarPagoSimple(this, registro);
    }

    void calcularBonificacion(Propietario propietario, Registro registro){
        Puesto puesto = registro.getPuesto();
        Vehiculo vehiculo = registro.getVehiculo();
        int montoTarifa = registro.getMontoTarifa();

        Asignacion asignacion = propietario.buscarAsignacionSegunPuesto(puesto);

        if (asignacion != null) {

            boolean segundoTransito =
                    asignacion.mismoBono("frecuente") &&
                    vehiculo.esSegundoTransitoDelDia(puesto, registro.getFecha());

            double montoBonificado =
                    asignacion.calcularMontoBonificado(montoTarifa, segundoTransito);

            registro.setMontoNombreBono(
                    montoBonificado,
                    asignacion.getBonificacionNombre()
            );
        }
    }

    void completarRegistro(Propietario propietario, Registro registro) throws SaldoException {
        descontarTransito(propietario, registro.calcularMontoPagar());
        registro.setMontoPagado();
        registro.getVehiculo().agregarRegistro(registro);
    }

    private void descontarTransito(Propietario propietario, double monto) throws SaldoException{

        int saldo = propietario.getSaldo();

        if(saldo < monto) throw new SaldoException("Saldo Actual: " + saldo + "\n Saldo insuficiente para cobrar el total: " + monto);

        //funciona si es Exonerado y el saldo es cero
        propietario.restarMonto(monto);
    }

    void notificarTransito(Propietario propietario, Registro registro) {
        propietario.agregarNotificacion_PAGO_REALIZADO(Notificacion.notificarTransito(registro.getPuestoNombre(), registro.getMatricula()));
    }
}