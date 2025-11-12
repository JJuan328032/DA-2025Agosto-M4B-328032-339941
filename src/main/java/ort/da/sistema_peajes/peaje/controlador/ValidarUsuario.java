package ort.da.sistema_peajes.peaje.controlador;

import jakarta.servlet.http.HttpSession;

public class ValidarUsuario {
    public static void validar(HttpSession sesionHttp, String tipo) throws Exception{
        if(sesionHttp.getAttribute(tipo) == null) throw new Exception("No tiene Permisos");
    }
}
