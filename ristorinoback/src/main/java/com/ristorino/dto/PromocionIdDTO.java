package com.ristorino.dto;

public class PromocionIdDTO {
    private int nroRestaurante;
    private int nroIdioma;
    private int nroContenido;

    public PromocionIdDTO() {
    }

    public int getNroRestaurante() {
        return nroRestaurante;
    }

    public void setNroRestaurante(int nroRestaurante) {
        this.nroRestaurante = nroRestaurante;
    }

    public int getNroIdioma() {
        return nroIdioma;
    }

    public void setNroIdioma(int nroIdioma) {
        this.nroIdioma = nroIdioma;
    }

    public int getNroContenido() {
        return nroContenido;
    }

    public void setNroContenido(int nroContenido) {
        this.nroContenido = nroContenido;
    }
}
