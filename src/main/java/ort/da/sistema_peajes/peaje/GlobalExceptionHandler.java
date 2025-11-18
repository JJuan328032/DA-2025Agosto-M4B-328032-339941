package ort.da.sistema_peajes.peaje;

import javax.security.auth.login.LoginException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import ort.da.sistema_peajes.peaje.exceptions.EstadoException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({LoginException.class, EstadoException.class})
    public ResponseEntity<String> handleAppExceptions(Exception ex) {
        // Se usa 299 para que vistaWeb.js pueda tratarlo como excepcion de aplicacion
        return ResponseEntity.status(299).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleUnexpected(Exception ex) {
        // Loguear para depuración en servidor
        ex.printStackTrace();
        String msg = "Error interno del servidor: " + (ex.getMessage() != null ? ex.getMessage() : "-");
        return ResponseEntity.status(500).body(msg);
    }

}
