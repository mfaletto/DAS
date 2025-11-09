package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable // Indica que esta clase se "incrustará" como una clave.
public class ContenidosRestaurantesId implements Serializable {

    @Column(name = "nro_restaurante")
    private Integer nroRestaurante;

    @Column(name = "nro_idioma")
    private Integer nroIdioma;

    @Column(name = "nro_contenido")
    private Integer nroContenido;

    // 💡 Consejo: Genera un constructor vacío,
    // Getters/Setters, y MÉTODOS equals() y hashCode().
    // (JPA requiere equals() y hashCode() para claves compuestas).

    // Constructor vacío
    public ContenidosRestaurantesId() {}

    // Getters y Setters...
    // ...


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

    // Métodos equals() y hashCode() (Generados por IntelliJ)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContenidosRestaurantesId that = (ContenidosRestaurantesId) o;
        return Objects.equals(nroRestaurante, that.nroRestaurante) &&
                Objects.equals(nroIdioma, that.nroIdioma) &&
                Objects.equals(nroContenido, that.nroContenido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nroRestaurante, nroIdioma, nroContenido);
    }
}