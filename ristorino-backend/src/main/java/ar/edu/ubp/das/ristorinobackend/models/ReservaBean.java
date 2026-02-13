package ar.edu.ubp.das.ristorinobackend.models;

import java.time.LocalDate;

public class ReservaBean {
    private int nroReserva;
    private int nroCliente;
    private int nroRestaurante;
    private LocalDate fechaReserva; // Usamos LocalDate (Java 8+)
    private String horaReserva;     // Usamos String por simplicidad ("20:30")
    private int cantAdultos;
    private int cantMenores;
    private String observaciones;

    // Constructor vacío
    public ReservaBean() {}

    // Getters y Setters
    public int getNroReserva() { return nroReserva; }
    public void setNroReserva(int nroReserva) { this.nroReserva = nroReserva; }

    public int getNroCliente() { return nroCliente; }
    public void setNroCliente(int nroCliente) { this.nroCliente = nroCliente; }

    public int getNroRestaurante() { return nroRestaurante; }
    public void setNroRestaurante(int nroRestaurante) { this.nroRestaurante = nroRestaurante; }

    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getHoraReserva() { return horaReserva; }
    public void setHoraReserva(String horaReserva) { this.horaReserva = horaReserva; }

    public int getCantAdultos() { return cantAdultos; }
    public void setCantAdultos(int cantAdultos) { this.cantAdultos = cantAdultos; }

    public int getCantMenores() { return cantMenores; }
    public void setCantMenores(int cantMenores) { this.cantMenores = cantMenores; }

    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}