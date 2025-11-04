package ort.da.sistema_peajes.peaje;

import ort.da.sistema_peajes.peaje.service.SeedData;

public class Precarga {
    public static void cargar() {
        // Carga de datos de prueba
        SeedData.cargarDatos();
    }
}
