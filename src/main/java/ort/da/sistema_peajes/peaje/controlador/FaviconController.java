package ort.da.sistema_peajes.peaje.controlador;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Small controller to handle browser requests for /favicon.ico.
 * Returns 204 No Content to avoid server 500 when no static favicon is present.
 * Replace by a static file in /static/favicon.ico when ready.
 */
@RestController
public class FaviconController {

    @GetMapping("/favicon.ico")
    public ResponseEntity<Void> favicon() {
        return ResponseEntity.noContent().build();
    }
}
