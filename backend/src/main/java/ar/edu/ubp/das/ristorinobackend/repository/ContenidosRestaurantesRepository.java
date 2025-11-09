package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.entity.ContenidosRestaurantes;
import ar.edu.ubp.das.ristorinobackend.entity.ContenidosRestaurantesId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenidosRestaurantesRepository extends JpaRepository<ContenidosRestaurantes, ContenidosRestaurantesId> {
    // 💡 JpaRepository<ClaseEntidad, TipoDeLaClaseIDCompuesta>
    //
    // Al extender JpaRepository, ya tenemos .findAll() para
    // obtener todas las promociones para la Home Page.
}