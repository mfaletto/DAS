package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "clicks_contenidos_restaurantes", schema = "dbo")
public class ClicksContenidosRestaurantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_click")
    private Integer nroClick;

    // Aquí definimos las relaciones (Foreign Keys)

    // Relación con el Cliente (Quién hizo clic)
    @ManyToOne
    @JoinColumn(name = "nro_cliente") // Columna de esta tabla (clicks_contenidos...)
    private Cliente cliente;

    // Relación con el Contenido (En qué se hizo clic)
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "nro_restaurante", referencedColumnName = "nro_restaurante"),
            @JoinColumn(name = "nro_idioma", referencedColumnName = "nro_idioma"),
            @JoinColumn(name = "nro_contenido", referencedColumnName = "nro_contenido")
    })
    private ContenidosRestaurantes contenido;

    @Column(name = "fecha_hora_registro", nullable = false)
    private LocalDateTime fechaHoraRegistro;

    @Column(name = "costo_click", precision = 10, scale = 2)
    private BigDecimal costoClick;

    @Column(name = "notificado", nullable = false)
    private boolean notificado;

    public Integer getNroClick() {
        return nroClick;
    }

    public void setNroClick(Integer nroClick) {
        this.nroClick = nroClick;
    }

    public ContenidosRestaurantes getContenido() {
        return contenido;
    }

    public void setContenido(ContenidosRestaurantes contenido) {
        this.contenido = contenido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getCostoClick() {
        return costoClick;
    }

    public void setCostoClick(BigDecimal costoClick) {
        this.costoClick = costoClick;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public boolean isNotificado() {
        return notificado;
    }

    public void setNotificado(boolean notificado) {
        this.notificado = notificado;
    }

    // 💡 Consejo: Genera constructores, Getters y Setters.
}