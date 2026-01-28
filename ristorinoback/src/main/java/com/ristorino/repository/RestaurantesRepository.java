package com.ristorino.repository;

import com.ristorino.dto.RestauranteDetalleDTO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.Map;

@Repository
public class RestaurantesRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public RestaurantesRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public RestauranteDetalleDTO obtenerDetalle(int id) {
        String sql = """
            SELECT TOP 1
              r.nro_restaurante,
              r.razon_social,
              r.cuit
            FROM dbo.restaurantes r WITH (NOLOCK)
            WHERE r.nro_restaurante = :id
        """;
        try {
            return jdbc.queryForObject(sql, Map.of("id", id), (rs, rn) -> {
                var dto = new RestauranteDetalleDTO();
                dto.setNroRestaurante(rs.getInt("nro_restaurante"));
                dto.setRazonSocial(rs.getString("razon_social"));
                dto.setCuit(rs.getString("cuit"));
                return dto;
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
