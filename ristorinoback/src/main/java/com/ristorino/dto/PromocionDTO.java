package com.ristorino.dto;

import java.time.OffsetDateTime;

public class PromocionDTO {
    private PromocionIdDTO id;
    private Integer nroSucursal;                 // puede venir null
    private String contenidoPromocional;
    private String imagenPromocional;
    private String contenidoAPublicar;
    private String fechaIniVigencia;             // enviar como string ISO para matchear front
    private String fechaFinVigencia;
    private Double costoClick;
    private String codContenidoRestaurante;

    public PromocionDTO() {

    }

    public PromocionIdDTO getId() {
        return id;
    }

    public void setId(PromocionIdDTO id) {
        this.id = id;
    }

    public Integer getNroSucursal() {
        return nroSucursal;
    }

    public void setNroSucursal(Integer nroSucursal) {
        this.nroSucursal = nroSucursal;
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

    public String getContenidoAPublicar() {
        return contenidoAPublicar;
    }

    public void setContenidoAPublicar(String contenidoAPublicar) {
        this.contenidoAPublicar = contenidoAPublicar;
    }

    public String getFechaIniVigencia() {
        return fechaIniVigencia;
    }

    public void setFechaIniVigencia(String fechaIniVigencia) {
        this.fechaIniVigencia = fechaIniVigencia;
    }

    public String getFechaFinVigencia() {
        return fechaFinVigencia;
    }

    public void setFechaFinVigencia(String fechaFinVigencia) {
        this.fechaFinVigencia = fechaFinVigencia;
    }

    public Double getCostoClick() {
        return costoClick;
    }

    public void setCostoClick(Double costoClick) {
        this.costoClick = costoClick;
    }

    public String getCodContenidoRestaurante() {
        return codContenidoRestaurante;
    }

    public void setCodContenidoRestaurante(String codContenidoRestaurante) {
        this.codContenidoRestaurante = codContenidoRestaurante;
    }
}
