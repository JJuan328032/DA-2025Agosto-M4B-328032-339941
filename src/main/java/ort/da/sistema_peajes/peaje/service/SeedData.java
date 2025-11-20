package ort.da.sistema_peajes.peaje.service;

import ort.da.sistema_peajes.peaje.model.Bonificacion.*;
import ort.da.sistema_peajes.peaje.model.Estados.Deshabilitado;
import ort.da.sistema_peajes.peaje.model.*;
import ort.da.sistema_peajes.peaje.model.Usuarios.*;

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


        Propietario cero = fachada.agregarPropietario("23456789", "prop.123", "Usuario Propietario", 2000, 500);
        fachada.agregarAdministrador("12345678", "admin.123", "Usuario Administrador");


        Propietario prop1 = fachada.agregarPropietario("1", "q", "Carlos Rodríguez", 4000, 1000);

        Propietario prop2 = fachada.agregarPropietario("2", "w", "Ana Martínez", 0, 500);

        Propietario desabilitado = fachada.agregarPropietario("3", "e", "Facundo Nieves", 1000, 200);
        desabilitado.setEstadoPropietario(new Deshabilitado(desabilitado));

        fachada.agregarAdministrador("1", "a", "Juan Pérez");
        fachada.agregarAdministrador("2", "s", "Manuel Kant");

        

        
        Bonificacion bonFrecuente = fachada.agregarBonificacion("frecuente");
        Bonificacion bonExonerado = fachada.agregarBonificacion("exonerado");
        Bonificacion bonTrabajador = fachada.agregarBonificacion("trabajador");


        ArrayList<Puesto> listaPuestos = new ArrayList<>();

        // Crear puestos con tarifas
        Puesto ruta1_1 = new Puesto("Ruta 1 - Barra Santa Lucía", "(km 23.500)", agregarTarifas());
        Puesto ruta1_2 = new Puesto("Ruta 1 - Cufré", "(km 107.350)", agregarTarifas2());
        
        Puesto ruta5_1 = new Puesto("Ruta 5 - Mendoza", "(km 67.700)", agregarTarifas());
        Puesto ruta5_2 = new Puesto("Ruta 5 - Centenario", "(km 246.350)", agregarTarifas2());
        Puesto ruta5_3 = new Puesto("Ruta 5 - Manuel Díaz", "(km 423.200)", agregarTarifas());

        listaPuestos.add(ruta1_1);
        listaPuestos.add(ruta1_2);
        listaPuestos.add(ruta5_1);
        listaPuestos.add(ruta5_2);
        listaPuestos.add(ruta5_3);

        fachada.agregarVariosPuestos(listaPuestos);

        // Asignar bonificaciones a propietarios
        Asignacion asig1 = new Asignacion(ruta1_1, bonFrecuente, LocalDate.of(2025, 1, 1));
        Asignacion asig2 = new Asignacion(ruta5_2, bonExonerado, LocalDate.of(2025, 1, 2));

        Asignacion asig3 = new Asignacion(ruta5_2, bonTrabajador, LocalDate.of(2025, 1, 2));

        prop1.agregarAsignacion(asig1);
        prop2.agregarAsignacion(asig2);
        cero.agregarAsignacion(asig3);

        // Crear vehículos

        ArrayList<Vehiculo> lista = new ArrayList<>();

        Vehiculo v1 = new Vehiculo("ABC123", "Toyota Corolla", "Rojo", "(A)Automóvil");
        Vehiculo v2 = new Vehiculo("DEF456", "Ford Ranger", "Blanco", "(B)Camioneta");
        Vehiculo v3 = new Vehiculo("GHI789", "Honda Civic", "Azul", "(A)Automóvil");
        Vehiculo v4 = new Vehiculo("JKL012", "Yamaha MT-07", "Negro", "(F)Moto");

        Vehiculo v5 = new Vehiculo("JHY769", "Corolla Cross", "Azul", "(A)Automóvil");
        Vehiculo v6 = new Vehiculo("MTU315", "Super Sport", "Negro", "(F)Moto");

        Vehiculo v7 = new Vehiculo("HEG356", "Ford Ranger", "Rojo", "(B)Camioneta");

        lista.add(v1);
        lista.add(v2);
        lista.add(v3);
        lista.add(v4);
        lista.add(v5);
        lista.add(v6);
        lista.add(v7);

        fachada.agregarVariosVehiculos(lista);

        v1.setPropietario(prop1);
        prop1.agregarVehiculo(v1);

        v2.setPropietario(prop1);
        prop1.agregarVehiculo(v2);

        v3.setPropietario(prop2);
        prop2.agregarVehiculo(v3);

        v4.setPropietario(prop2);
        prop2.agregarVehiculo(v4);

        v7.setPropietario(desabilitado);
        desabilitado.agregarVehiculo(v7);

        v5.setPropietario(cero);
        cero.agregarVehiculo(v5);

        v6.setPropietario(cero);
        cero.agregarVehiculo(v6);

        // Crear registros (tránsitos)
        Tarifa tAuto = ruta1_1.getTarifas().get(0);
        Tarifa tCamioneta = ruta1_1.getTarifas().get(1);
        Tarifa tMoto = ruta5_2.getTarifas().get(4);

        Registro r1 = new Registro(ruta1_1, v1, LocalDateTime.of(2025, 10, 26, 8, 15), tAuto);
        r1.setBonificacion(bonFrecuente.getNombre());
        r1.setMontoBonificado(20);
        r1.setMontoPagado();
        fachada.agregarRegistro(r1);
        v1.agregarRegistro(r1);

        //System.out.println("Primer Registro: " + r1);

        Registro r2 = new Registro(ruta1_2, v2, LocalDateTime.of(2025, 10, 27, 8, 30), tCamioneta);
        r2.setBonificacion(bonExonerado.getNombre());
        r2.setMontoBonificado(0);
        r2.setMontoPagado();
        fachada.agregarRegistro(r2);
        v2.agregarRegistro(r2);

        //System.out.println("Segundo Registro: " + r2);

        Registro r3 = new Registro(ruta5_1, v3, LocalDateTime.of(2025, 10, 28, 8, 20), tAuto);
        r3.setBonificacion(bonExonerado.getNombre());
        r3.setMontoBonificado(50);
        r3.setMontoPagado();
        fachada.agregarRegistro(r3);
        v3.agregarRegistro(r3);

        //System.out.println("Tercer Registro: " + r3);

        Registro r4 = new Registro(ruta5_2, v4, LocalDateTime.of(2025, 10, 29, 8, 42), tMoto);
        r4.setBonificacion(bonFrecuente.getNombre());
        r4.setMontoBonificado(0);
        r4.setMontoPagado();
        fachada.agregarRegistro(r4);
        v4.agregarRegistro(r4);

        //System.out.println("Cuarto Registro: " + r4);

        Registro r5 = new Registro(ruta5_3, v1, LocalDateTime.of(2025, 10, 30, 8, 10), tAuto);
        r5.setBonificacion(bonExonerado.getNombre());
        r5.setMontoBonificado(10);
        r5.setMontoPagado();
        fachada.agregarRegistro(r5);
        v1.agregarRegistro(r5);

        //System.out.println("Quinto Registro: " + r5);

        // Agregar notificaciones
        prop1.agregarNotificacion("Se asignó bonificación Frecuente al Peaje Ruta 1");
        prop1.agregarNotificacion("Nuevo tránsito registrado en Peaje Ruta 1 con vehículo ABC123");
        prop2.agregarNotificacion("Se asignó bonificación Trabajador al Peaje Ruta 5");

        System.out.println("Datos de prueba cargados exitosamente.");
    }

    private static ArrayList<Tarifa> agregarTarifas() {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        tarifas.add(new Tarifa("Automóvil", 170, "(A)Automóvil"));
        tarifas.add(new Tarifa("Camioneta", 250, "(B)Camioneta"));
        tarifas.add(new Tarifa("Camión", 320, "(C)Camión"));
        tarifas.add(new Tarifa("Ómnibus", 350, "(D)Ómnibus"));
        tarifas.add(new Tarifa("Moto", 58, "(F)Moto"));
        
        return tarifas;
    }

    private static ArrayList<Tarifa> agregarTarifas2() {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        tarifas.add(new Tarifa("Automóvil", 100, "(A)Automóvil"));
        tarifas.add(new Tarifa("Camioneta", 150, "(B)Camioneta"));
        tarifas.add(new Tarifa("Camión", 300, "(C)Camión"));
        tarifas.add(new Tarifa("Ómnibus", 250, "(D)Ómnibus"));
        tarifas.add(new Tarifa("Moto", 50, "(F)Moto"));
        
        return tarifas;
    }
}