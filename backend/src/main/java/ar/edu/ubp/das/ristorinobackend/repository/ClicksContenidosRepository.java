package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.bean.ClickBean;
import ar.edu.ubp.das.ristorinobackend.component.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClicksContenidosRepository {

    private static final String SCHEMA = "dbo";
    private static final String RS_CLICKS = "clicks";
    private static final String RS_VALIDACION = "resultado";
    private static final String RS_REGISTRO = "resultado";

    private static final String SP_REGISTRAR_CLICK = "reg_click_promocion";
    private static final String SP_ACTUALIZAR_CLICK = "upd_click_promocion";
    private static final String SP_CLICKS_PENDIENTES = "get_clicks_pendientes_notificacion";
    private static final String SP_VALIDAR_CLICK = "valida_click_promocion";

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    public ClickBean registrarClick(ClickBean click) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_cliente", click.getNroCliente())
                .addValue("nro_restaurante", click.getNroRestaurante())
                .addValue("nro_idioma", click.getNroIdioma())
                .addValue("nro_contenido", click.getNroContenido())
                .addValue("costo_click", click.getCostoClick())
                .addValue("fecha_hora_registro", java.sql.Timestamp.valueOf(click.getFechaHoraRegistro()))
                .addValue("notificado", Boolean.FALSE);

        List<RegistroResultado> resultados = jdbcCallFactory.executeQuery(
                SP_REGISTRAR_CLICK,
                SCHEMA,
                params,
                RS_REGISTRO,
                RegistroResultado.class
        );

        if (resultados != null && !resultados.isEmpty()) {
            click.setNroClick(resultados.get(0).getNroClick());
        }
        click.setNotificado(Boolean.FALSE);
        return click;
    }

    public void actualizarEstadoNotificado(ClickBean click) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_click", click.getNroClick())
                .addValue("notificado", Boolean.TRUE);
        jdbcCallFactory.execute(SP_ACTUALIZAR_CLICK, SCHEMA, params);
    }

    public List<ClickBean> obtenerClicksPendientes() {
        return jdbcCallFactory.executeQuery(
                SP_CLICKS_PENDIENTES,
                SCHEMA,
                RS_CLICKS,
                ClickBean.class
        );
    }

    public boolean existeClickReciente(ClickBean click) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_cliente", click.getNroCliente())
                .addValue("nro_restaurante", click.getNroRestaurante())
                .addValue("nro_idioma", click.getNroIdioma())
                .addValue("nro_contenido", click.getNroContenido());

        List<ValidacionClick> resultado = jdbcCallFactory.executeQuery(
                SP_VALIDAR_CLICK,
                SCHEMA,
                params,
                RS_VALIDACION,
                ValidacionClick.class
        );

        return resultado != null
                && !resultado.isEmpty()
                && Boolean.TRUE.equals(resultado.get(0).getExisteClick());
    }

    @SuppressWarnings("unused")
    private static class ValidacionClick {
        private Boolean existeClick;

        public Boolean getExisteClick() {
            return existeClick;
        }

        public void setExisteClick(Boolean existeClick) {
            this.existeClick = existeClick;
        }
    }

    @SuppressWarnings("unused")
    private static class RegistroResultado {
        private Integer nroClick;

        public Integer getNroClick() {
            return nroClick;
        }

        public void setNroClick(Integer nroClick) {
            this.nroClick = nroClick;
        }
    }
}
