package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.entity.SucursalRestaurante;
import ar.edu.ubp.das.ristorinobackend.entity.SucursalRestauranteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SucursalRestauranteRepository extends JpaRepository<SucursalRestaurante, SucursalRestauranteId> {
    // Utiliza la clase de clave compuesta
}