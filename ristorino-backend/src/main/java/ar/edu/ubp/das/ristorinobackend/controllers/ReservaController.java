package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.models.ReservaBean;
import ar.edu.ubp.das.ristorinobackend.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @PostMapping("/crear")
    @Transactional // Si el SP falla, Spring hace rollback automático
    public ResponseEntity<?> crearReserva(@RequestBody ReservaBean reserva) {
        try {
            // Validaciones básicas
            if (reserva.getNroCliente() <= 0 || reserva.getNroRestaurante() <= 0) {
                return ResponseEntity.badRequest().body("Datos de cliente o restaurante inválidos");
            }

            // Llamamos al repositorio que ejecuta el SP
            int idGenerado = reservaRepository.crearReserva(reserva);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Reserva creada con éxito",
                    "nro_reserva", idGenerado
            ));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al reservar: " + e.getMessage());
        }
    }
}