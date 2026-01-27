package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.bean.RestauranteBean;
import ar.edu.ubp.das.ristorinobackend.component.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RestauranteRepository {

    private static final String SCHEMA = "dbo";
    private static final String RS_RESTAURANTE = "restaurante";
    private static final String SP_RESTAURANTE_POR_ID = "get_restaurante_por_id";

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    public Optional<RestauranteBean> findById(Integer nroRestaurante) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_restaurante", nroRestaurante);

        List<RestauranteBean> result = (List<RestauranteBean>) jdbcCallFactory.executeQueryWithOutputs(
                SP_RESTAURANTE_POR_ID,
                SCHEMA,
                params,
                RS_RESTAURANTE,
                RestauranteBean.class
        ).get(RS_RESTAURANTE);

        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }
}
