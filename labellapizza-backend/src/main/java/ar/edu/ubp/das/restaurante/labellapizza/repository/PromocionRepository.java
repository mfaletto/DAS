package ar.edu.ubp.das.restaurante.labellapizza.repository;

import ar.edu.ubp.das.restaurante.labellapizza.model.Promocion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PromocionRepository {

    // Simulamos promociones para la Sucursal 1 (Centro)
    public List<Promocion> obtenerPorSucursal(int idSucursal) {
        List<Promocion> lista = new ArrayList<>();

        if (idSucursal == 1) { // Centro
            lista.add(new Promocion("2x1 en Muzza", "Todos los martes llevando 2 muzzarellas", "BANNER", true));
            lista.add(new Promocion("Noche de Amigos", "Descuento en cervezas artesanales", "TEXTO", true));
        } else if (idSucursal == 2) { // Cerro
            lista.add(new Promocion("Menu Ejecutivo", "Pizza individual + Bebida", "TEXTO", true));
        }

        return lista;
    }
}