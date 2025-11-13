package ar.edu.ubp.das.ristorinobackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Controlador raíz de la API que proporciona información sobre los endpoints disponibles.
 */
@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:4200")
public class ApiController {

    /**
     * Endpoint raíz que muestra información de la API.
     * 
     * URL: GET http://localhost:8080/api/v1/
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getApiInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("nombre", "Ristorino API");
        info.put("version", "1.0");
        info.put("estado", "activo");
        
        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("promociones", "/api/v1/promociones");
        endpoints.put("promociones_clic", "/api/v1/promociones/clic");
        endpoints.put("restaurantes", "/api/v1/restaurantes/{id}");
        endpoints.put("auth_register", "/api/v1/auth/register");
        endpoints.put("notificaciones", "/api/v1/notificaciones/procesar");
        
        info.put("endpoints", endpoints);
        
        return ResponseEntity.ok(info);
    }
}

