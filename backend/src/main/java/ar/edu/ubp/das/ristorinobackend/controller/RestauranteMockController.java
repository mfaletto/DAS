package ar.edu.ubp.das.ristorinobackend.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/notificaciones")
@CrossOrigin(origins = "*")
public class RestauranteMockController {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteMockController.class);

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
        logger.info("Código Contenido Restaurante: {}", notificacion.get("codContenidoRestaurante"));
        logger.info("=============================================");

        // Simulación de procesamiento por parte del restaurante
        return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Notificación recibida correctamente",
                "nroClick", notificacion.get("nroClick"),
                "timestamp", System.currentTimeMillis()
        ));
    }
}


