package ar.edu.ubp.das.ristorinobackend.repository;

import ar.edu.ubp.das.ristorinobackend.bean.ContenidoBean;
import ar.edu.ubp.das.ristorinobackend.bean.ContenidoIdBean;
import ar.edu.ubp.das.ristorinobackend.component.SimpleJdbcCallFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ContenidosRestaurantesRepository {

    private static final String SCHEMA = "dbo";
    private static final String RS_PROMOCIONES = "promociones";
    private static final String RS_PROMOCION = "promocion";

    private static final String SP_PROMOCIONES_DISPONIBLES = "get_promociones_disponibles";
    private static final String SP_PROMOCION_POR_ID = "get_promocion_por_id";

    @Autowired
    private SimpleJdbcCallFactory jdbcCallFactory;

    public List<ContenidoBean> findPromocionesDisponibles(Integer nroPortal, Integer nroIdioma) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_portal", nroPortal)
                .addValue("nro_idioma", nroIdioma);
        List<ContenidoPlano> resultados = jdbcCallFactory.executeQuery(
                SP_PROMOCIONES_DISPONIBLES,
                SCHEMA,
                params,
                RS_PROMOCIONES,
                ContenidoPlano.class
        );

        return resultados.stream()
                .map(this::mapearContenido)
                .collect(Collectors.toList());
    }

    public Optional<ContenidoBean> findById(ContenidoIdBean id) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("nro_restaurante", id.getNroRestaurante())
                .addValue("nro_idioma", id.getNroIdioma())
                .addValue("nro_contenido", id.getNroContenido());

        Map<String, Object> out = jdbcCallFactory.executeQueryWithOutputs(
                SP_PROMOCION_POR_ID,
                SCHEMA,
                params,
                RS_PROMOCION,
                ContenidoPlano.class
        );

        List<ContenidoPlano> result = (List<ContenidoPlano>) out.get(RS_PROMOCION);
        if (result == null || result.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapearContenido(result.get(0)));
    }

    private ContenidoBean mapearContenido(ContenidoPlano plano) {
        ContenidoIdBean id = new ContenidoIdBean();
        id.setNroRestaurante(plano.getNroRestaurante());
        id.setNroIdioma(plano.getNroIdioma());
        id.setNroContenido(plano.getNroContenido());

        ContenidoBean bean = new ContenidoBean();
        bean.setId(id);
        bean.setContenidoPromocional(plano.getContenidoPromocional());
        bean.setImagenPromocional(plano.getImagenPromocional());
        bean.setFechaIniVigencia(plano.getFechaIniVigencia());
        bean.setFechaFinVigencia(plano.getFechaFinVigencia());
        bean.setCostoClick(plano.getCostoClick());
        return bean;
    }

    @SuppressWarnings("unused")
    private static class ContenidoPlano {
        private Integer nroRestaurante;
        private Integer nroIdioma;
        private Integer nroContenido;
        private String contenidoPromocional;
        private String imagenPromocional;
        private java.time.LocalDateTime fechaIniVigencia;
        private java.time.LocalDateTime fechaFinVigencia;
        private java.math.BigDecimal costoClick;

        public Integer getNroRestaurante() { return nroRestaurante; }
        public void setNroRestaurante(Integer nroRestaurante) { this.nroRestaurante = nroRestaurante; }
        public Integer getNroIdioma() { return nroIdioma; }
        public void setNroIdioma(Integer nroIdioma) { this.nroIdioma = nroIdioma; }
        public Integer getNroContenido() { return nroContenido; }
        public void setNroContenido(Integer nroContenido) { this.nroContenido = nroContenido; }
        public String getContenidoPromocional() { return contenidoPromocional; }
        public void setContenidoPromocional(String contenidoPromocional) { this.contenidoPromocional = contenidoPromocional; }
        public String getImagenPromocional() { return imagenPromocional; }
        public void setImagenPromocional(String imagenPromocional) { this.imagenPromocional = imagenPromocional; }
        public java.time.LocalDateTime getFechaIniVigencia() { return fechaIniVigencia; }
        public void setFechaIniVigencia(java.time.LocalDateTime fechaIniVigencia) { this.fechaIniVigencia = fechaIniVigencia; }
        public java.time.LocalDateTime getFechaFinVigencia() { return fechaFinVigencia; }
        public void setFechaFinVigencia(java.time.LocalDateTime fechaFinVigencia) { this.fechaFinVigencia = fechaFinVigencia; }
        public java.math.BigDecimal getCostoClick() { return costoClick; }
        public void setCostoClick(java.math.BigDecimal costoClick) { this.costoClick = costoClick; }
    }
}
