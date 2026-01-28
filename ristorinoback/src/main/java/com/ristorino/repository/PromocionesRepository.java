package com.ristorino.repository;

import com.ristorino.dto.PromocionDTO;
import com.ristorino.dto.PromocionIdDTO;
import com.ristorino.util.Mappers;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;

@Repository
public class PromocionesRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public PromocionesRepository(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<PromocionDTO> listarPromocionesVigentes() {
        String sql = """
  SELECT TOP 50
    c.nro_restaurante, c.nro_idioma, c.nro_contenido, c.nro_sucursal,
    c.contenido_promocional, c.imagen_promocional, c.contenido_a_publicar,
    c.fecha_ini_vigencia, c.fecha_fin_vigencia, c.costo_click, c.cod_contenido_restaurante
  FROM dbo.contenidos_restaurantes c WITH (NOLOCK)
  WHERE
    (c.fecha_ini_vigencia IS NULL OR c.fecha_ini_vigencia <= SYSUTCDATETIME())
    AND (c.fecha_fin_vigencia IS NULL OR c.fecha_fin_vigencia >= SYSUTCDATETIME())
""";
        return jdbc.query(sql, Map.of(), Mappers.PROMOCION_ROW_MAPPER);
    }
}
