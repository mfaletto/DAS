package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.service.PortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/portal")
public class PortalController {

    @Autowired
    private PortalService portalService;

    @PostMapping("/disponibilidad")
    public ResponseEntity<?> buscarDisponibilidad(@RequestBody Map<String, Object> body) {
        // Body esperado: { "idRestaurante": 2, "criterios": { "tipoCocina": "Nikkei" } }

        try {
            int idRestaurante = (int) body.get("idRestaurante");
            Map<String, String> criterios = (Map<String, String>) body.get("criterios");

            Object resultado = portalService.consultarDisponibilidad(idRestaurante, criterios);
            return ResponseEntity.ok(resultado);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}