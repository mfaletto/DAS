package ar.edu.ubp.das.ristorinobackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador que simula el servicio REST de un restaurante de ejemplo.
 * 
 * Este controlador recibe las notificaciones de clics desde Ristorino.
 * En un escenario real, este sería un servicio externo del restaurante.
 * 
 * Para el entregable, este controlador simula cómo un restaurante recibiría
 * las notificaciones de clics en sus promociones.
 */
@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class RestauranteMockController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteMockController.class);

    /**
     * Endpoint que simula la recepción de notificaciones de clics por parte de un restaurante.
     * 
     * En producción, este endpoint estaría en un servidor diferente (servicio del restaurante).
     * Para este entregable, lo incluimos en el mismo backend para demostrar la funcionalidad.
     * 
     * URL: POST http://localhost:8081/api/notificaciones/click
     * (Nota: En producción sería un servidor diferente, aquí usamos el mismo para simular)
     */
    @PostMapping("/click")
    public ResponseEntity<Map<String, Object>> recibirNotificacionClick(@RequestBody Map<String, Object> notificacion) {
        logger.info("=== NOTIFICACIÓN RECIBIDA DESDE RISTORINO ===");
        logger.info("Restaurante: {} (ID: {})", 
                notificacion.get("razonSocial"), 
                notificacion.get("nroRestaurante"));
        logger.info("Clic ID: {}", notificacion.get("nroClick"));
        logger.info("Fecha/Hora: {}", notificacion.get("fechaHoraRegistro"));
        logger.info("Costo del clic: ${}", notificacion.get("costoClick"));
        logger.info("Promoción: {}", notificacion.get("contenidoPromocional"));
        logger.info("=============================================");
        
        // Simular procesamiento de la notificación
        // En un escenario real, el restaurante podría:
        // - Registrar el clic en su propio sistema
        // - Actualizar estadísticas
        // - Generar facturación
        // - etc.
        
        // Respuesta de éxito
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Notificación recibida correctamente",
                "nroClick", notificacion.get("nroClick"),
                "timestamp", System.currentTimeMillis()
        ));
    }
}

