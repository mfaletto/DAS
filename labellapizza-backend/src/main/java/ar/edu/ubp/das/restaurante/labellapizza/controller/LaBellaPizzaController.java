package ar.edu.ubp.das.restaurante.labellapizza.controller;

import ar.edu.ubp.das.restaurante.labellapizza.config.SucursalParam;
import ar.edu.ubp.das.restaurante.labellapizza.model.Sucursal;
import ar.edu.ubp.das.restaurante.labellapizza.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class LaBellaPizzaController {

    @Autowired
    private SucursalRepository repository;

    @Autowired
    private ar.edu.ubp.das.restaurante.labellapizza.repository.PromocionRepository promoRepository;

    // Endpoint 1: Listar todas (para ver qué IDs tenemos y hashearlos manualmente)
    @GetMapping("/sucursales")
    public ResponseEntity<List<Map<String, Object>>> listarParaPruebas() {
        List<Sucursal> sucursales = repository.listarTodas();

        // Transformamos a un mapa con el HASH para poder probar
        List<Map<String, Object>> respuesta = sucursales.stream().map(s -> {
            Map<String, Object> map = new HashMap<>();
            map.put("nombre", s.getNombre());
            // Generamos el hash en vivo para que puedas copiar y pegar en Postman
            String hash = Base64.getEncoder().encodeToString(String.valueOf(s.getId()).getBytes());
            map.put("hash_para_probar", hash);
            return map;
        }).toList();

        return ResponseEntity.ok(respuesta);
    }

    // Endpoint 2: Consultar Disponibilidad (¡AQUÍ USAMOS EL RESOLVER!)
    // Fijate: No recibimos String hash, recibimos directamente el objeto Sucursal
    @GetMapping("/disponibilidad")
    public ResponseEntity<?> consultar(@SucursalParam Sucursal sucursal) {

        Map<String, Object> body = new HashMap<>();
        body.put("mensaje", "Sucursal válida encontrada");
        body.put("sucursal_real", sucursal.getNombre());
        body.put("direccion", sucursal.getDireccion());
        body.put("turnos_libres", List.of("20:00", "21:30"));

        return ResponseEntity.ok(body);
    }

    // Endpoint 3: Obtener Promociones (Marketing)
    @GetMapping("/marketing")
    public ResponseEntity<?> obtenerPromociones(@SucursalParam Sucursal sucursal) {

        // El Resolver ya buscó la sucursal y validó el hash por nosotros.
        // Solo nos preocupamos por la lógica de negocio.
        List<ar.edu.ubp.das.restaurante.labellapizza.model.Promocion> promos =
                promoRepository.obtenerPorSucursal(sucursal.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("sucursal", sucursal.getNombre());
        response.put("contenido_marketing", promos);

        return ResponseEntity.ok(response);
    }
}