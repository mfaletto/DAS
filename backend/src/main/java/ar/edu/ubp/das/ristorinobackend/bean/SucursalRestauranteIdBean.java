package ar.edu.ubp.das.ristorinobackend.bean;

import java.io.Serializable;
import java.util.Objects;

public class SucursalRestauranteIdBean implements Serializable {

    private Integer nroRestaurante;
    private Integer nroSucursal;

    public Integer getNroRestaurante() {
        return nroRestaurante;
    }

    public void setNroRestaurante(Integer nroRestaurante) {
        this.nroRestaurante = nroRestaurante;
    }

    public Integer getNroSucursal() {
        return nroSucursal;
    }

    public void setNroSucursal(Integer nroSucursal) {
        this.nroSucursal = nroSucursal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SucursalRestauranteIdBean that = (SucursalRestauranteIdBean) o;
        return Objects.equals(nroRestaurante, that.nroRestaurante)
                && Objects.equals(nroSucursal, that.nroSucursal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroRestaurante, nroSucursal);
    }
}
