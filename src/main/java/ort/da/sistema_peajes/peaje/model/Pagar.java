package ort.da.sistema_peajes.peaje.model;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;
import ort.da.sistema_peajes.peaje.exceptions.SaldoException;
import ort.da.sistema_peajes.peaje.model.Usuarios.Propietario;

public class Pagar {

    public void realizarPagoSimple(Propietario propietario, Registro registro)
        throws EstadoException, SaldoException {

        if (!propietario.validarEstado()) {
            return;
        }

        // Si no es bonificable, finalizamos directo
        if (!propietario.esBonificable()) {
            completarRegistro(propietario, registro);
            return;
        }

        // A partir de aquí SIEMPRE es bonificable
        Puesto puesto = registro.getPuesto();
        Vehiculo vehiculo = registro.getVehiculo();
        int montoTarifa = registro.getMontoTarifa();

        Asignacion asignacion = propietario.buscarAsignacionSegunPuesto(puesto);

        if (asignacion != null) {

            boolean segundoTransito =
                    asignacion.mismoBono("frecuente") &&
                    propietario.esSegundoTransitoDelDia(puesto, vehiculo, registro.getFecha());

            double montoBonificado =
                    asignacion.calcularMontoBonificado(montoTarifa, segundoTransito);

            registro.setMontoNombreBono(
                    montoBonificado,
                    asignacion.getBonificacionNombre()
            );
        }

        notificarTransito(propietario, puesto.getNombre(), vehiculo.getMatricula());
        completarRegistro(propietario, registro);
    }



    private static void completarRegistro(Propietario propietario, Registro registro) throws SaldoException {
        descontarTransito(propietario, registro.calcularMontoPagar());
        registro.setMontoPagado();
        propietario.agregarRegistro(registro);

        //TODO cuando el prop esta suspendido El transito se registra, pero no se aplican...
        //bonificaciones (si hubiera) y no se envía la notificación al propietario.
        //el transito no se está guardando en la lista de registros porque solo se actualiza cuando llama a avisar
    }


    private static void descontarTransito(Propietario propietario, double monto) throws SaldoException{

        int saldo = propietario.getSaldo();

        if(saldo < monto) throw new SaldoException("Saldo Actual: " + saldo + "\n Saldo insuficiente para cobrar el total: " + monto);

        //funciona si es Exonerado y el saldo es cero
        propietario.restarMonto(monto);
    }

    

    private static void notificarTransito(Propietario propietario, String puesto, String matricula) {
        propietario.agregarNotificacion_PAGO_REALIZADO(Notificacion.notificarTransito(puesto, matricula));
    }
}
