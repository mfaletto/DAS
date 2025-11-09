package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.entity.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    // Hereda .findById(id) que usamos en RestauranteService
}