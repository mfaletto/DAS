package com.ristorino.repository;

import com.ristorino.dto.ClickRequest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class ClicksRepository {
    private final NamedParameterJdbcTemplate jdbc;

    public ClicksRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void insertarClick(ClickRequest req) {
        // Traer costo_click desde contenidos_restaurantes en el INSERT
        String sql = """
            INSERT INTO dbo.clicks_contenidos_restaurantes
              (nro_restaurante, nro_idioma, nro_contenido, fecha_hora_registro, nro_cliente, costo_click, notificado)
            SELECT
              :nro_restaurante, :nro_idioma, :nro_contenido, SYSUTCDATETIME(), :nro_cliente, c.costo_click, 0
            FROM dbo.contenidos_restaurantes c
            WHERE c.nro_restaurante = :nro_restaurante
              AND c.nro_idioma = :nro_idioma
              AND c.nro_contenido = :nro_contenido
        """;
        jdbc.update(sql, Map.of(
                "nro_restaurante", req.getNroRestaurante(),
                "nro_idioma", req.getNroIdioma(),
                "nro_contenido", req.getNroContenido(),
                "nro_cliente", req.getNroCliente()
        ));
    }
}
