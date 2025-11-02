package ort.da.sistema_peajes.peaje.datos;

import ort.da.sistema_peajes.peaje.model.Bonificacion.*;
import ort.da.sistema_peajes.peaje.model.*;
import ort.da.sistema_peajes.peaje.model.Usuarios.*;
import ort.da.sistema_peajes.peaje.service.Fachada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 * Clase para generar datos de prueba representativos para el sistema de peajes.
 * Incluye datos para todas las entidades principales del sistema.
 */
public class SeedData {
    private static Fachada fachada = Fachada.getInstancia();

    public static void cargarDatos() {
        System.out.println("Cargando datos de prueba...");

        // Crear usuarios

        Propietario prop1 = fachada.agregarPropietario("p", "p", "Carlos Rodríguez");
        prop1.setSaldo(4000);

        Propietario prop2 = fachada.agregarPropietario("prop2", "prop456", "Ana Martínez");
        prop2.setSaldo(3000);
        fachada.agregarAdministrador("a", "a", "Juan Pérez");

        // Crear bonificaciones
        Bonificacion bonFrecuente = new Descuento(20, "Frecuente");
        Bonificacion bonTrabajador = new Descuento(50, "Trabajador");

        // Crear puestos con tarifas
        Puesto peaje1 = new Puesto("Peaje Ruta 1", "Km 56 Ruta 1");
        Puesto peaje2 = new Puesto("Peaje Ruta 5", "Km 98 Ruta 5");

        agregarTarifas(peaje1);
        agregarTarifas2(peaje2);

        try{
            fachada.agregarPuesto(peaje1);
            fachada.agregarPuesto(peaje2);
        } catch (Exception e){
            System.out.println("Error al agregar puestos: " + e.getMessage());
        }


        // Asignar bonificaciones a propietarios
        Asignacion asig1 = new Asignacion(peaje1, bonFrecuente, LocalDate.of(2025, 1, 1));
        Asignacion asig2 = new Asignacion(peaje2, bonTrabajador, LocalDate.of(2025, 1, 2));
        prop1.getAsignaciones().add(asig1);
        prop2.getAsignaciones().add(asig2);

        // Crear vehículos

        Vehiculo v1 = new Vehiculo("ABC123", "Toyota Corolla", "Rojo", "(A)Automóvil");
        Vehiculo v2 = new Vehiculo("DEF456", "Ford Ranger", "Blanco", "(B)Camioneta");
        Vehiculo v3 = new Vehiculo("GHI789", "Honda Civic", "Azul", "(A)Automóvil");
        Vehiculo v4 = new Vehiculo("JKL012", "Yamaha MT-07", "Negro", "(F)Moto");

        fachada.agregarVehiculo(v1);
        fachada.agregarVehiculo(v2);
        fachada.agregarVehiculo(v3);
        fachada.agregarVehiculo(v4);

        v1.setPropietario(prop1);
        prop1.agregarVehiculo(v1);

        v2.setPropietario(prop1);
        prop1.agregarVehiculo(v2);

        v3.setPropietario(prop2);
        prop2.agregarVehiculo(v3);

        v4.setPropietario(prop2);
        prop2.agregarVehiculo(v4);

        // Crear registros (tránsitos)
        Tarifa tAuto = peaje1.getTarifas().get(0);
        Tarifa tCamioneta = peaje1.getTarifas().get(1);
        Tarifa tMoto = peaje2.getTarifas().get(4);

        Registro r1 = new Registro(peaje1, v1, LocalDateTime.of(2025, 10, 26, 8, 15), tAuto);
        r1.setBonificacion(bonFrecuente.getNombre());
        r1.setMontoBonificado(20);
        r1.setMontoPagado();
        fachada.agregarRegistro(r1);
        prop1.agregarRegistro(r1);

        //System.out.println("Primer Registro: " + r1);

        Registro r2 = new Registro(peaje1, v2, LocalDateTime.of(2025, 10, 27, 8, 30), tCamioneta);
        r2.setBonificacion(bonTrabajador.getNombre());
        r2.setMontoBonificado(0);
        r2.setMontoPagado();
        fachada.agregarRegistro(r2);
        prop1.agregarRegistro(r2);

        //System.out.println("Segundo Registro: " + r2);

        Registro r3 = new Registro(peaje2, v3, LocalDateTime.of(2025, 10, 28, 8, 20), tAuto);
        r3.setBonificacion(bonTrabajador.getNombre());
        r3.setMontoBonificado(50);
        r3.setMontoPagado();
        fachada.agregarRegistro(r3);
        prop2.agregarRegistro(r3);

        //System.out.println("Tercer Registro: " + r3);

        Registro r4 = new Registro(peaje2, v4, LocalDateTime.of(2025, 10, 29, 8, 42), tMoto);
        r4.setBonificacion(bonFrecuente.getNombre());
        r4.setMontoBonificado(0);
        r4.setMontoPagado();
        fachada.agregarRegistro(r4);
        prop2.agregarRegistro(r4);

        //System.out.println("Cuarto Registro: " + r4);

        Registro r5 = new Registro(peaje1, v1, LocalDateTime.of(2025, 10, 30, 8, 10), tAuto);
        r5.setBonificacion(bonTrabajador.getNombre());
        r5.setMontoBonificado(10);
        r5.setMontoPagado();
        fachada.agregarRegistro(r5);
        prop1.agregarRegistro(r5);

        //System.out.println("Quinto Registro: " + r5);

        // Agregar notificaciones
        prop1.agregarNotificacion("Se asignó bonificación Frecuente al Peaje Ruta 1");
        prop1.agregarNotificacion("Nuevo tránsito registrado en Peaje Ruta 1 con vehículo ABC123");
        prop2.agregarNotificacion("Se asignó bonificación Trabajador al Peaje Ruta 5");

        System.out.println("Datos de prueba cargados exitosamente.");
    }

    private static void agregarTarifas(Puesto puesto) {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        tarifas.add(new Tarifa("Automóvil", 170, "(A)Automóvil"));
        tarifas.add(new Tarifa("Camioneta", 250, "(B)Camioneta"));
        tarifas.add(new Tarifa("Camión", 320, "(C)Camión"));
        tarifas.add(new Tarifa("Ómnibus", 350, "(D)Ómnibus"));
        tarifas.add(new Tarifa("Moto", 58, "(F)Moto"));
        puesto.setTarifas(tarifas);
    }

    private static void agregarTarifas2(Puesto puesto) {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        tarifas.add(new Tarifa("Automóvil", 100, "(A)Automóvil"));
        tarifas.add(new Tarifa("Camioneta", 150, "(B)Camioneta"));
        tarifas.add(new Tarifa("Camión", 300, "(C)Camión"));
        tarifas.add(new Tarifa("Ómnibus", 250, "(D)Ómnibus"));
        tarifas.add(new Tarifa("Moto", 50, "(F)Moto"));
        puesto.setTarifas(tarifas);
    }
}