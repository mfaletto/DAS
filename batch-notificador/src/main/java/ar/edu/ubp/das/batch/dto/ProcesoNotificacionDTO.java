package ar.edu.ubp.das.batch.dto;

public class ProcesoNotificacionDTO {

    private int total;
    private int exitosos;
    private int fallidos;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getExitosos() {
        return exitosos;
    }

    public void setExitosos(int exitosos) {
        this.exitosos = exitosos;
    }

    public int getFallidos() {
        return fallidos;
    }

    public void setFallidos(int fallidos) {
        this.fallidos = fallidos;
    }
}
