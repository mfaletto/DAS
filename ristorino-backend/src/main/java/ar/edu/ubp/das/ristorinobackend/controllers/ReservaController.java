package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.models.ReservaBean;
import ar.edu.ubp.das.ristorinobackend.service.ReservaService; // Ojo al import nuevo
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService; // <--- Usamos Service, NO Repository

    @PostMapping("/crear")
    // Ya NO va @Transactional acá
    public ResponseEntity<?> crearReserva(@RequestBody ReservaBean reserva) {
        try {
            // El controller delega todo al servicio
            int idGenerado = reservaService.crearReserva(reserva);

            return ResponseEntity.ok(Map.of(
                    "mensaje", "Reserva creada con éxito",
                    "nro_reserva", idGenerado
            ));

        } catch (Exception e) {
            // Si el servicio tira error (por validación o base de datos), cae acá
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error al reservar: " + e.getMessage());
        }
    }

    // AGREGAR ESTE ENDPOINT (Req 16):
    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<ReservaBean>> obtenerReservasUsuario(@PathVariable int id) {
        List<ReservaBean> reservas = reservaService.listarReservasPorCliente(id);
        return ResponseEntity.ok(reservas);
    }
}