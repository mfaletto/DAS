package ar.edu.ubp.das.ristorinobackend.models;

public class ClienteBean {
    private int nroCliente;
    private String apellido;
    private String nombre;
    private String clave;
    private String correo;
    private String telefonos;
    private boolean habilitado;

    // Constructor vacío
    public ClienteBean() {}

    // Getters y Setters
    public int getNroCliente() { return nroCliente; }
    public void setNroCliente(int nroCliente) { this.nroCliente = nroCliente; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getClave() { return clave; }
    public void setClave(String clave) { this.clave = clave; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefonos() { return telefonos; }
    public void setTelefonos(String telefonos) { this.telefonos = telefonos; }

    public boolean isHabilitado() { return habilitado; }
    public void setHabilitado(boolean habilitado) { this.habilitado = habilitado; }
}