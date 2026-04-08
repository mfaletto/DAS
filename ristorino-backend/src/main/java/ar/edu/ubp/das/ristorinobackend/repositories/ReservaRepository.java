package ar.edu.ubp.das.ristorinobackend.repositories;

import ar.edu.ubp.das.ristorinobackend.models.ReservaBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReservaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. EL MAPPER (Traductor SQL -> Java)
    private static final class ReservaMapper implements RowMapper<ReservaBean> {
        @Override
        public ReservaBean mapRow(ResultSet rs, int rowNum) throws SQLException {
            ReservaBean r = new ReservaBean();
            r.setNroReserva(rs.getInt("nro_reserva"));
            r.setNroCliente(rs.getInt("nro_cliente"));
            r.setNroRestaurante(rs.getInt("nro_restaurante"));
            // Conversiones importantes de Fecha y Hora
            r.setFechaReserva(rs.getDate("fecha_reserva").toLocalDate());
            r.setHoraReserva(rs.getString("hora_reserva").substring(0, 5)); // 20:00:00 -> 20:00
            r.setCantAdultos(rs.getInt("cant_adultos"));
            r.setCantMenores(rs.getInt("cant_menores"));
            r.setObservaciones(rs.getString("observaciones"));
            return r;
        }
    }

    // 2. CREAR RESERVA (Tu código original)
    public int crearReserva(ReservaBean reserva) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("sp_crear_reserva");

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("nro_cliente", reserva.getNroCliente());
        inParams.put("nro_restaurante", reserva.getNroRestaurante());
        inParams.put("fecha_reserva", java.sql.Date.valueOf(reserva.getFechaReserva()));
        inParams.put("hora_reserva", Time.valueOf(reserva.getHoraReserva() + ":00"));
        inParams.put("cant_adultos", reserva.getCantAdultos());
        inParams.put("cant_menores", reserva.getCantMenores());
        inParams.put("observaciones", reserva.getObservaciones());

        Map<String, Object> outParams = jdbcCall.execute(inParams);
        return (int) outParams.get("id_reserva_generado");
    }

    // 3. LEER RESERVAS (¡ESTO ES LO QUE FALTABA!)
    public List<ReservaBean> getReservasPorCliente(int nroCliente) {
        // Asegurate que la tabla en tu SQL se llame 'reservas_restaurantes'
        String sql = "SELECT * FROM reservas_restaurantes WHERE nro_cliente = ? ORDER BY fecha_reserva DESC";
        return jdbcTemplate.query(sql, new ReservaMapper(), nroCliente);
    }
}