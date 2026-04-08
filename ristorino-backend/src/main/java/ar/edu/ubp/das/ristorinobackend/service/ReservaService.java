package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.models.ReservaBean;
import ar.edu.ubp.das.ristorinobackend.repositories.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Transactional // <--- La transacción va AQUÍ, en el servicio
    public int crearReserva(ReservaBean reserva) throws Exception {

        // 1. Movemos la validación que tenías en el controller para acá
        if (reserva.getNroCliente() <= 0 || reserva.getNroRestaurante() <= 0) {
            throw new Exception("Datos de cliente o restaurante inválidos");
        }

        // 2. Llamamos al repo (igual que antes)
        return reservaRepository.crearReserva(reserva);
    }

    // AGREGAR ESTE MÉTODO:
    public List<ReservaBean> listarReservasPorCliente(int idCliente) {
        return reservaRepository.getReservasPorCliente(idCliente);
    }
}