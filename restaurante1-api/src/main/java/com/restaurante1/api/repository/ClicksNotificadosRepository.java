package com.restaurante1.api.repository;

import com.restaurante1.api.dto.ClickNotificacionDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public class ClicksNotificadosRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ClicksNotificadosRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertar(ClickNotificacionDTO click) {
        String sql = """
            INSERT INTO dbo.clicks_notificados (
                nro_click, nro_restaurante, nro_idioma, nro_contenido, 
                fecha_hora_registro, nro_cliente, costo_click, notificado
            ) VALUES (
                :nroClick, :nroRestaurante, :nroIdioma, :nroContenido, 
                :fechaHoraRegistro, :nroCliente, :costoClick, :notificado
            )
            """;

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("nroClick", click.nroClick())
            .addValue("nroRestaurante", click.nroRestaurante())
            .addValue("nroIdioma", click.nroIdioma())
            .addValue("nroContenido", click.nroContenido())
            .addValue("fechaHoraRegistro", LocalDateTime.parse(click.fechaHoraRegistro()))
            .addValue("nroCliente", click.nroCliente())
            .addValue("costoClick", click.costoClick())
            .addValue("notificado", click.notificado());

        jdbcTemplate.update(sql, params);
    }
}
