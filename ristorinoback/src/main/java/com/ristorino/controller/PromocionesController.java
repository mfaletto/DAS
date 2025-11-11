package com.ristorino.controller;

import com.ristorino.dto.PromocionDTO;
import com.ristorino.dto.RestauranteDetalleDTO;
import com.ristorino.service.PromocionesService;
import com.ristorino.service.RestaurantesService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class PromocionesController {

    private final PromocionesService promocionesService;
    private final RestaurantesService restaurantesService;

    public PromocionesController(PromocionesService p, RestaurantesService r) {
        this.promocionesService = p;
        this.restaurantesService = r;
    }

    @GetMapping("/promociones")
    public List<PromocionDTO> listarPromociones() {
        return promocionesService.listarPromocionesVigentes(); // para Home
    }

    @GetMapping("/restaurantes/{id}")
    public RestauranteDetalleDTO getRestaurante(@PathVariable int id) {
        return restaurantesService.obtenerDetalle(id);
    }
}
