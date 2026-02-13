package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.service.MarketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/marketing")
public class MarketingController {

    @Autowired
    private MarketingService marketingService;

    // Endpoint 1: El Front pide banners para mostrar
    @GetMapping("/contenidos")
    public ResponseEntity<?> verBanners(@RequestParam int idRestaurante) {
        return ResponseEntity.ok(marketingService.obtenerContenido(idRestaurante));
    }

    // Endpoint 2: El usuario hizo clic en un banner
    @PostMapping("/click")
    public ResponseEntity<?> registrarClick(@RequestBody Map<String, Object> body) {
        // Body: { "idRestaurante": 1, "idCliente": 1, "titulo": "2x1 en Muzza" }
        int idRestaurante = (int) body.get("idRestaurante");
        int idCliente = (int) body.get("idCliente");
        String titulo = (String) body.get("titulo");

        String resultado = marketingService.registrarInteraccion(idRestaurante, idCliente, titulo);
        return ResponseEntity.ok(Map.of("mensaje", resultado));
    }
}