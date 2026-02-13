package ar.edu.ubp.das.ristorinobackend.repositories;

import ar.edu.ubp.das.ristorinobackend.models.ReservaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReservaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int crearReserva(ReservaBean reserva) {
        // Usamos SimpleJdbcCall para manejar los parámetros OUT más fácil
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_crear_reserva");

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("nro_cliente", reserva.getNroCliente());
        inParams.put("nro_restaurante", reserva.getNroRestaurante());
        inParams.put("fecha_reserva", java.sql.Date.valueOf(reserva.getFechaReserva())); // LocalDate -> SQL Date
        inParams.put("hora_reserva", Time.valueOf(reserva.getHoraReserva() + ":00"));   // String "20:00" -> SQL Time
        inParams.put("cant_adultos", reserva.getCantAdultos());
        inParams.put("cant_menores", reserva.getCantMenores());
        inParams.put("observaciones", reserva.getObservaciones());

        // Ejecutamos el SP
        Map<String, Object> outParams = jdbcCall.execute(inParams);

        // Obtenemos el ID generado (Spring lo devuelve con el nombre del parámetro OUT)
        return (int) outParams.get("id_reserva_generado");
    }
}