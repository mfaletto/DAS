package ar.edu.ubp.das.ristorinobackend.repositories;

import ar.edu.ubp.das.ristorinobackend.models.ClienteBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapper: Transforma la fila de la DB a Objeto Java
    private static final class ClienteMapper implements RowMapper<ClienteBean> {
        @Override
        public ClienteBean mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClienteBean c = new ClienteBean();
            c.setNroCliente(rs.getInt("nro_cliente"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido(rs.getString("apellido"));
            c.setCorreo(rs.getString("correo"));
            c.setClave(rs.getString("clave"));
            c.setTelefonos(rs.getString("telefonos"));
            c.setHabilitado(rs.getBoolean("habilitado"));
            return c;
        }
    }

    // LOGIN: Buscar usuario por correo y clave
    public ClienteBean buscarPorCorreoYClave(String correo, String clave) {
        String sql = "SELECT * FROM clientes WHERE correo = ? AND clave = ? AND habilitado = 1";
        List<ClienteBean> resultados = jdbcTemplate.query(sql, new ClienteMapper(), correo, clave);

        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }

    // VALIDACIÓN: Chequear si el mail ya existe (Req 1 - Obligatorio)
    public boolean existeUsuario(String correo) {
        String sql = "SELECT count(*) FROM clientes WHERE correo = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, correo);
        return count != null && count > 0;
    }

    // REGISTRO: Insertar nuevo usuario usando MapSqlParameterSource + NamedParameterJdbcTemplate
    public int registrar(ClienteBean c) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo, clave, telefonos, habilitado) " +
                     "VALUES (:nombre, :apellido, :correo, :clave, :telefonos, 1)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("nombre", c.getNombre())
                .addValue("apellido", c.getApellido())
                .addValue("correo", c.getCorreo())
                .addValue("clave", c.getClave())
                .addValue("telefonos", c.getTelefonos());

        NamedParameterJdbcTemplate namedTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        return namedTemplate.update(sql, params);
    }

    // EXTRAS: Método para buscar reservas por ID de cliente (Para Req 16)
    // Este lo usaremos cuando hagamos la pantalla "Mis Reservas"
    /*
    public ClienteBean buscarPorId(int id) {
        String sql = "SELECT * FROM clientes WHERE nro_cliente = ?";
        List<ClienteBean> resultados = jdbcTemplate.query(sql, new ClienteMapper(), id);
        return resultados.isEmpty() ? null : resultados.get(0);
    }
    */
}