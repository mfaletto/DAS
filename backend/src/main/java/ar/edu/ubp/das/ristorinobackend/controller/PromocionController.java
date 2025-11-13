package ar.edu.ubp.das.ristorinobackend.controller;

import ar.edu.ubp.das.ristorinobackend.bean.ContenidoBean;
import ar.edu.ubp.das.ristorinobackend.dto.ClickRequestDTO;
import ar.edu.ubp.das.ristorinobackend.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // ⬅️ 1. Marca la clase como un Controlador REST (recibe peticiones HTTP)
@RequestMapping("/promociones") // ⬅️ 2. Define la URL base para este controlador (ej: /api/v1/promociones)
@CrossOrigin(origins = "http://localhost:4200") // ⬅️ 3. CRUCIAL: Permite que Angular (en localhost:4200) llame a esta API
public class PromocionController {

    // 4. Inyectamos el "cerebro" (el Servicio) que creamos en el paso 2.2
    @Autowired
    private PromocionService promocionService;

    /**
     * ENDPOINT 1: Obtener todas las promociones
     * Requisito: "visualicen las promociones publicadas por los restaurantes"
     *
     * URL: GET http://localhost:8080/api/v1/promociones
     */
    @GetMapping
    public ResponseEntity<List<ContenidoBean>> getPromociones() {
        // Llama al servicio, que llama al repositorio, que trae los datos de SQL Server
        List<ContenidoBean> promociones = promocionService.getPromociones();
        return ResponseEntity.ok(promociones); // Devuelve la lista como JSON con un estado 200 OK
    }

    /**
     * ENDPOINT 2: Registrar el clic de monetización
     * Requisito: "Al hacer clic sobre una promoción, el sistema deberá registrarlo para monetizar"
     *
     * URL: POST http://localhost:8080/api/v1/promociones/clic
     */
    @PostMapping("/clic")
    public ResponseEntity<Void> registrarClic(@RequestBody ClickRequestDTO clickDTO) {
        // @RequestBody le dice a Spring que convierta el JSON de Angular en nuestro objeto ClickRequestDTO
        try {
            // Llama al servicio para ejecutar la lógica de negocio (guardar en la DB)
            promocionService.registrarClic(clickDTO);
            return ResponseEntity.ok().build(); // Devuelve un 200 OK si todo salió bien
        } catch (Exception e) {
            // Manejo básico de errores (ej: si la promoción no se encontró)
            return ResponseEntity.badRequest().build(); // Devuelve un 400 Bad Request
        }
    }

    // NOTA: El Endpoint GET /api/v1/restaurantes/{id} (para el detalle)
    // lo crearemos después, ya que requiere las entidades Restaurante y Sucursal.
    // Con estos dos endpoints, ya podemos construir la Fase 3 en Angular.
}