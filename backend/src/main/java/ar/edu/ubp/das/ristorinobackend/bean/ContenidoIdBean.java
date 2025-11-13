package ar.edu.ubp.das.ristorinobackend.bean;

import java.io.Serializable;
import java.util.Objects;

public class ContenidoIdBean implements Serializable {

    private Integer nroRestaurante;
    private Integer nroIdioma;
    private Integer nroContenido;

    public Integer getNroRestaurante() {
        return nroRestaurante;
    }

    public void setNroRestaurante(Integer nroRestaurante) {
        this.nroRestaurante = nroRestaurante;
    }

    public Integer getNroIdioma() {
        return nroIdioma;
    }

    public void setNroIdioma(Integer nroIdioma) {
        this.nroIdioma = nroIdioma;
    }

    public Integer getNroContenido() {
        return nroContenido;
    }

    public void setNroContenido(Integer nroContenido) {
        this.nroContenido = nroContenido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContenidoIdBean that = (ContenidoIdBean) o;
        return Objects.equals(nroRestaurante, that.nroRestaurante)
                && Objects.equals(nroIdioma, that.nroIdioma)
                && Objects.equals(nroContenido, that.nroContenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroRestaurante, nroIdioma, nroContenido);
    }
}
