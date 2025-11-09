package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contenidos_restaurantes", schema = "dbo")
public class ContenidosRestaurantes {

    @EmbeddedId // ⬅️ Usa la clave compuesta que definimos.
    private ContenidosRestaurantesId id;

    // Mapeamos los campos que usaremos para mostrar la promoción
    @Column(name = "contenido_promocional")
    private String contenidoPromocional; // Este será el "Título" o "Descripción"

    @Column(name = "imagen_promocional", length = 500)
    private String imagenPromocional; // La URL de la imagen

    @Column(name = "fecha_ini_vigencia")
    private LocalDateTime fechaIniVigencia;

    @Column(name = "fecha_fin_vigencia")
    private LocalDateTime fechaFinVigencia;

    @Column(name = "costo_click", precision = 10, scale = 2)
    private BigDecimal costoClick; // Usamos BigDecimal para dinero (DECIMAL(10,2))

    public ContenidosRestaurantesId getId() {
        return id;
    }

    public void setId(ContenidosRestaurantesId id) {
        this.id = id;
    }

    public String getContenidoPromocional() {
        return contenidoPromocional;
    }

    public void setContenidoPromocional(String contenidoPromocional) {
        this.contenidoPromocional = contenidoPromocional;
    }

    public String getImagenPromocional() {
        return imagenPromocional;
    }

    public void setImagenPromocional(String imagenPromocional) {
        this.imagenPromocional = imagenPromocional;
    }

    public LocalDateTime getFechaIniVigencia() {
        return fechaIniVigencia;
    }

    public void setFechaIniVigencia(LocalDateTime fechaIniVigencia) {
        this.fechaIniVigencia = fechaIniVigencia;
    }

    public LocalDateTime getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(LocalDateTime fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public BigDecimal getCostoClick() {
        return costoClick;
    }

    public void setCostoClick(BigDecimal costoClick) {
        this.costoClick = costoClick;
    }

    // 💡 Consejo: Genera constructores, Getters y Setters.
}