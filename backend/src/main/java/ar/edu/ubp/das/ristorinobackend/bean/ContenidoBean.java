package ar.edu.ubp.das.ristorinobackend.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ContenidoBean {

    private ContenidoIdBean id;
    private String contenidoPromocional;
    private String imagenPromocional;
    private String imagenPromocionalHash;
    private String imagenPromocionalUrl;
    private LocalDateTime fechaIniVigencia;
    private LocalDateTime fechaFinVigencia;
    private BigDecimal costoClick;

    public ContenidoIdBean getId() {
        return id;
    }

    public void setId(ContenidoIdBean id) {
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

    public String getImagenPromocionalHash() {
        return imagenPromocionalHash;
    }

    public void setImagenPromocionalHash(String imagenPromocionalHash) {
        this.imagenPromocionalHash = imagenPromocionalHash;
    }

    public String getImagenPromocionalUrl() {
        return imagenPromocionalUrl;
    }

    public void setImagenPromocionalUrl(String imagenPromocionalUrl) {
        this.imagenPromocionalUrl = imagenPromocionalUrl;
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
}
