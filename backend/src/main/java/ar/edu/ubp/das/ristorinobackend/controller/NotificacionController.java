package ar.edu.ubp.das.ristorinobackend.controller;

import ar.edu.ubp.das.ristorinobackend.service.ProcesoNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar el proceso de notificación de clics.
 * 
 * Este controlador permite:
 * - Ejecutar manualmente el proceso de notificación (útil para pruebas)
 * - Ver el estado del proceso
 */
@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "http://localhost:4200")
public class NotificacionController {

    @Autowired
    private ProcesoNotificacionService procesoNotificacionService;

    /**
     * Endpoint para ejecutar manualmente el proceso de notificación.
     * 
     * Útil para pruebas y para ejecutar el proceso de forma inmediata sin esperar
     * al siguiente ciclo programado.
     * 
     * URL: POST http://localhost:8080/api/v1/notificaciones/procesar
     */
    @PostMapping("/procesar")
    public ResponseEntity<String> procesarClicsPendientes() {
        try {
            procesoNotificacionService.procesarClicsPendientesManual();
            return ResponseEntity.ok("Proceso de notificación ejecutado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al ejecutar el proceso: " + e.getMessage());
        }
    }
}

