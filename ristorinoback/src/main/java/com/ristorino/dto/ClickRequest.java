package com.ristorino.dto;

public class ClickRequest {
    private int nroRestaurante;
    private int nroIdioma;
    private int nroContenido;
    private Integer nroCliente; // opcional

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

    public Integer getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(Integer nroCliente) {
        this.nroCliente = nroCliente;
    }
}
