package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable // Indica que esta clase es la clave incrustada
public class SucursalRestauranteId implements Serializable {

    @Column(name = "nro_restaurante")
    private Integer nroRestaurante;

    @Column(name = "nro_sucursal")
    private Integer nroSucursal;

    // ⚠️ IMPORTANTE: Generar el constructor vacío, y los métodos equals() y hashCode()
    // (JPA lo requiere para manejar claves compuestas).

    public SucursalRestauranteId() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SucursalRestauranteId that = (SucursalRestauranteId) o;
        return Objects.equals(nroRestaurante, that.nroRestaurante) &&
                Objects.equals(nroSucursal, that.nroSucursal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroRestaurante, nroSucursal);
    }
}