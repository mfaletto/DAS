package com.ristorino.service;

import com.ristorino.dto.RestauranteDetalleDTO;
import com.ristorino.repository.RestaurantesRepository;
import org.springframework.stereotype.Service;

@Service
public class RestaurantesService {
    private final RestaurantesRepository repo;

    public RestaurantesService(RestaurantesRepository repo) {
        this.repo = repo;
    }

    public RestauranteDetalleDTO obtenerDetalle(int id) {
        return repo.obtenerDetalle(id);
    }
}
