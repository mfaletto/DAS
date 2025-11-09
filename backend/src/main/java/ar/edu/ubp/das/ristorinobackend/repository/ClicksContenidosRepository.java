package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.entity.ClicksContenidosRestaurantes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClicksContenidosRepository extends JpaRepository<ClicksContenidosRestaurantes, Integer> {
    // 💡 JpaRepository<ClaseEntidad, TipoDeLaID (nro_click)>
    //
    // Usaremos principalmente el método .save(click)
    // que heredamos de JpaRepository.
    // Cuando llamemos a .save(), JPA insertará un nuevo
    // registro en la tabla dbo.clicks_contenidos_restaurantes.
    
    /**
     * Busca todos los clics que aún no han sido notificados a los restaurantes.
     * Este método será usado por el proceso programado de notificación.
     */
    @Query("SELECT c FROM ClicksContenidosRestaurantes c WHERE c.notificado = false")
    List<ClicksContenidosRestaurantes> findClicksNoNotificados();
}