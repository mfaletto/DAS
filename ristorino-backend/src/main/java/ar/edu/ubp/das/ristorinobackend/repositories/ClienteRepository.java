package ar.edu.ubp.das.ristorinobackend.repositories;

import ar.edu.ubp.das.ristorinobackend.models.ClienteBean; // Asumo que crearemos este Bean luego
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Mapper manual: Transforma una fila de SQL (ResultSet) a un Objeto Java
    private static final class ClienteMapper implements RowMapper<ClienteBean> {
        @Override
        public ClienteBean mapRow(ResultSet rs, int rowNum) throws SQLException {
            ClienteBean c = new ClienteBean();
            c.setNroCliente(rs.getInt("nro_cliente"));
            c.setNombre(rs.getString("nombre"));
            c.setApellido(rs.getString("apellido"));
            c.setCorreo(rs.getString("correo"));
            c.setClave(rs.getString("clave"));
            c.setHabilitado(rs.getBoolean("habilitado"));
            return c;
        }
    }

    // Método para LOGIN: Buscar usuario por correo y clave
    public ClienteBean buscarPorCorreoYClave(String correo, String clave) {
        String sql = "SELECT * FROM clientes WHERE correo = ? AND clave = ? AND habilitado = 1";
        List<ClienteBean> resultados = jdbcTemplate.query(sql, new ClienteMapper(), correo, clave);

        if (resultados.isEmpty()) {
            return null;
        }
        return resultados.get(0);
    }

    // Método para REGISTRO: Insertar nuevo usuario
    public int registrar(ClienteBean c) {
        String sql = "INSERT INTO clientes (nombre, apellido, correo, clave, telefonos, habilitado) VALUES (?, ?, ?, ?, ?, 1)";
        return jdbcTemplate.update(sql, c.getNombre(), c.getApellido(), c.getCorreo(), c.getClave(), c.getTelefonos());
    }
}