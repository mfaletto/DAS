package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.bean.SucursalRestauranteBean;
import ar.edu.ubp.das.ristorinobackend.bean.SucursalRestauranteIdBean;
import ar.edu.ubp.das.ristorinobackend.component.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class SucursalRestauranteRepository {

    private static final String SCHEMA = "dbo";
    private static final String RS_SUCURSALES = "sucursales";
    private static final String RS_SUCURSAL = "sucursal";

    private static final String SP_SUCURSALES_POR_RESTAURANTE = "get_sucursales_restaurante";
    private static final String SP_SUCURSAL_POR_ID = "get_sucursal_restaurante";

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    public List<SucursalRestauranteBean> findByRestaurante(Integer nroRestaurante) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_restaurante", nroRestaurante);
        return jdbcCallFactory.executeQuery(
                SP_SUCURSALES_POR_RESTAURANTE,
                SCHEMA,
                params,
                RS_SUCURSALES,
                SucursalRestauranteBean.class
        );
    }

    public Optional<SucursalRestauranteBean> findById(SucursalRestauranteIdBean id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_restaurante", id.getNroRestaurante())
                .addValue("nro_sucursal", id.getNroSucursal());

        Map<String, Object> out = jdbcCallFactory.executeQueryWithOutputs(
                SP_SUCURSAL_POR_ID,
                SCHEMA,
                params,
                RS_SUCURSAL,
                SucursalRestauranteBean.class
        );

        List<SucursalRestauranteBean> result = (List<SucursalRestauranteBean>) out.get(RS_SUCURSAL);
        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}