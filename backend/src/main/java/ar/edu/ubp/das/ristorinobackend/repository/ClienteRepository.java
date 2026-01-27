package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.bean.ClienteBean;
import ar.edu.ubp.das.ristorinobackend.component.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ClienteRepository {

    private static final String SCHEMA = "dbo";
    private static final String SP_REGISTRAR_CLIENTE = "reg_cliente";
    private static final String SP_CLIENTE_POR_CORREO = "get_cliente_por_correo";
    private static final String RS_CLIENTE = "cliente";

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    public ClienteBean registrar(ClienteBean cliente) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("apellido", cliente.getApellido())
                .addValue("nombre", cliente.getNombre())
                .addValue("clave_hash", cliente.getClaveHash())
                .addValue("correo", cliente.getCorreo())
                .addValue("telefonos", cliente.getTelefonos())
                .addValue("nro_localidad", cliente.getNroLocalidad())
                .addValue("habilitado", cliente.getHabilitado());

        Map<String, Object> out = jdbcCallFactory.executeWithOutputs(SP_REGISTRAR_CLIENTE, SCHEMA, params);
        Object nroCliente = out.get("nro_cliente");
        if (nroCliente != null) {
            cliente.setNroCliente(Integer.parseInt(nroCliente.toString()));
        }
        return cliente;
    }

    public Optional<ClienteBean> findByCorreo(String correo) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("correo", correo);
        Map<String, Object> out = jdbcCallFactory.executeQueryWithOutputs(
                SP_CLIENTE_POR_CORREO,
                SCHEMA,
                params,
                RS_CLIENTE,
                ClienteBean.class
        );
        List<ClienteBean> result = (List<ClienteBean>) out.get(RS_CLIENTE);
        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
