package ar.edu.ubp.das.ristorinobackend.repositories;

import ar.edu.ubp.das.ristorinobackend.models.RestauranteBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RestauranteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final class RestauranteMapper implements RowMapper<RestauranteBean> {
        @Override
        public RestauranteBean mapRow(ResultSet rs, int rowNum) throws SQLException {
            RestauranteBean r = new RestauranteBean();
            r.setId(rs.getInt("nro_restaurante"));
            r.setRazonSocial(rs.getString("razon_social"));
            r.setTipoConexion(rs.getString("tipo_conexion"));
            r.setEndpointUrl(rs.getString("endpoint_url"));
            return r;
        }
    }

    public RestauranteBean buscarPorId(int id) {
        String sql = "SELECT * FROM restaurantes WHERE nro_restaurante = ?";
        List<RestauranteBean> res = jdbcTemplate.query(sql, new RestauranteMapper(), id);
        return res.isEmpty() ? null : res.get(0);
    }

    // Listar todos usando BeanPropertyRowMapper (mapeo automatico por convención snake_case -> camelCase)
    public List<RestauranteBean> listarTodos() {
        String sql = "SELECT nro_restaurante AS id, razon_social AS razonSocial, " +
                     "tipo_conexion AS tipoConexion, endpoint_url AS endpointUrl FROM restaurantes";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(RestauranteBean.class));
    }
}