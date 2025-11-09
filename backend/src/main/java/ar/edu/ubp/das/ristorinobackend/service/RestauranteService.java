package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.entity.Restaurante;
import ar.edu.ubp.das.ristorinobackend.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Optional<Restaurante> getRestaurantePorId(Integer nroRestaurante) {
        // Busca el restaurante por su ID (nro_restaurante)
        return restauranteRepository.findById(nroRestaurante);
    }

    // 💡 Aquí iría la lógica para buscar Sucursales, Menús, etc.
}