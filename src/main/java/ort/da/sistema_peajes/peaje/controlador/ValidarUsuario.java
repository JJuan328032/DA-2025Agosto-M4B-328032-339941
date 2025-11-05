package ort.da.sistema_peajes.peaje.controlador;

import jakarta.servlet.http.HttpSession;

public class ValidarUsuario {

    public static void validar(HttpSession sesionHttp, String valor) throws Exception{
        if(sesionHttp.getAttribute(valor) == null) throw new Exception("No tiene permisos o la sesión expiró");
    }

}
