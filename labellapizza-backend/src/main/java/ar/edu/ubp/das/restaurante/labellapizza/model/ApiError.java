package ar.edu.ubp.das.restaurante.labellapizza.model;

public class ApiError {
    private int estado;
    private String mensaje;
    private String origen; // Para saber en qué microservicio pasó

    public ApiError(int estado, String mensaje, String origen) {
        this.estado = estado;
        this.mensaje = mensaje;
        this.origen = origen;
    }

    // Getters y Setters manuales
    public int getEstado() { return estado; }
    public void setEstado(int estado) { this.estado = estado; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getOrigen() { return origen; }
    public void setOrigen(String origen) { this.origen = origen; }
}