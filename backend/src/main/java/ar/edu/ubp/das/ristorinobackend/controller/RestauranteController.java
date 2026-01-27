package ar.edu.ubp.das.ristorinobackend.controller;

import ar.edu.ubp.das.ristorinobackend.bean.RestauranteBean;
import ar.edu.ubp.das.ristorinobackend.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurantes") // ⬅️ URL base: /api/v1/restaurantes
@CrossOrigin(origins = "http://localhost:4200")
public class RestauranteController {

    @Autowired
    private RestauranteService restauranteService;

    /**
     * ENDPOINT: GET /api/v1/restaurantes/{id}
     * Requisito: Muestra información del restaurante para la vista de detalle.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RestauranteBean> getRestaurante(@PathVariable("id") Integer id) {
        Optional<RestauranteBean> restaurante = restauranteService.getRestaurantePorId(id);

        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get()); // 200 OK con datos
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}