package ar.edu.ubp.das.restaurante.labellapizza.repository;

import ar.edu.ubp.das.restaurante.labellapizza.model.Sucursal;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class SucursalRepository {

    // Esta lista ESTÁTICA actúa como tu tabla de base de datos
    // Al ser static, los datos sobreviven mientras la app esté prendida
    private static final List<Sucursal> baseDeDatos = new ArrayList<>();

    // Bloque estático para cargar datos apenas arranca la clase (El "Loader" simple)
    static {
        baseDeDatos.add(new Sucursal(1, "La Bella Pizza - Centro", "Av. General Paz 120"));
        baseDeDatos.add(new Sucursal(2, "La Bella Pizza - Cerro", "Rafael Nuñez 4500"));
    }

    // Método para simular un "SELECT * FROM sucursales"
    public List<Sucursal> listarTodas() {
        return baseDeDatos;
    }

    // Método para simular un "SELECT * FROM sucursales WHERE id = ?"
    public Optional<Sucursal> buscarPorId(int id) {
        return baseDeDatos.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }
}