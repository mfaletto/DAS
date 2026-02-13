package ar.edu.ubp.das.restaurante.labellapizza.model;

public class Promocion {
    private String titulo;
    private String descripcion;
    private String tipo; // "BANNER", "TEXTO", "VIDEO"
    private boolean activa;

    public Promocion(String titulo, String descripcion, String tipo, boolean activa) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.activa = activa;
    }

    // Getters y Setters manuales
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public boolean isActiva() { return activa; }
    public void setActiva(boolean activa) { this.activa = activa; }
}