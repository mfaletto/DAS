package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.entity.ClicksContenidosRestaurantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClicksContenidosRepository extends JpaRepository<ClicksContenidosRestaurantes, Integer> {
    // 💡 JpaRepository<ClaseEntidad, TipoDeLaID (nro_click)>
    //
    // Usaremos principalmente el método .save(click)
    // que heredamos de JpaRepository.
    // Cuando llamemos a .save(), JPA insertará un nuevo
    // registro en la tabla dbo.clicks_contenidos_restaurantes.
}