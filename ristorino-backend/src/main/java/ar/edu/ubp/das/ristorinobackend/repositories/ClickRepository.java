package ar.edu.ubp.das.ristorinobackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public class ClickRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // MODIFICADO: Ahora llama al Stored Procedure
    public void registrarClick(int idRestaurante, int idCliente, String titulo) {
        // Sintaxis para llamar a un SP en SQL Server con JDBC
        String sql = "EXEC sp_registrar_click ?, ?, ?";

        // JdbcTemplate se encarga de pasar los parámetros en orden
        jdbcTemplate.update(sql, idRestaurante, idCliente, titulo);
    }

    // Validación Anti-Fraude (F5)
    // Verifica si este usuario YA hizo clic en este contenido HOY
    public boolean yaHizoClickHoy(int idRestaurante, int idCliente, String titulo) {
        String sql = """
            SELECT COUNT(*) FROM clicks_contenidos 
            WHERE nro_restaurante = ? 
            AND nro_cliente = ? 
            AND titulo_contenido = ? 
            AND CAST(fecha_hora_registro AS DATE) = CAST(GETDATE() AS DATE)
        """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, idRestaurante, idCliente, titulo);
        return count != null && count > 0;
    }
}