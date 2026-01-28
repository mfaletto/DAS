package com.ristorino.util;

import com.ristorino.dto.PromocionDTO;
import com.ristorino.dto.PromocionIdDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

public class Mappers {

    public static final RowMapper<PromocionDTO> PROMOCION_ROW_MAPPER = new RowMapper<>() {
        @Override
        public PromocionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
            var id = new PromocionIdDTO();
            id.setNroRestaurante(rs.getInt("nro_restaurante"));
            id.setNroIdioma(rs.getInt("nro_idioma"));
            id.setNroContenido(rs.getInt("nro_contenido"));

            var dto = new PromocionDTO();
            dto.setId(id);
            dto.setNroSucursal((Integer) rs.getObject("nro_sucursal"));
            dto.setContenidoPromocional(rs.getString("contenido_promocional"));
            dto.setImagenPromocional(rs.getString("imagen_promocional"));
            dto.setContenidoAPublicar(rs.getString("contenido_a_publicar"));

            // Fechas como string ISO (el front usa strings)
            var ini = rs.getTimestamp("fecha_ini_vigencia");
            var fin = rs.getTimestamp("fecha_fin_vigencia");
            dto.setFechaIniVigencia(ini != null ? ini.toInstant().toString() : null);
            dto.setFechaFinVigencia(fin != null ? fin.toInstant().toString() : null);

            dto.setCostoClick(rs.getObject("costo_click") != null ? rs.getDouble("costo_click") : null);
            dto.setCodContenidoRestaurante(rs.getString("cod_contenido_restaurante"));
            return dto;
        }
    };
}
