package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.*; // Usa 'jakarta.persistence' para Spring Boot 3+

@Entity // Le dice a JPA que esta clase representa una tabla.
@Table(name = "clientes", schema = "dbo") // Indica el nombre exacto de la tabla y el esquema.
public class Cliente {

    @Id // Marca esta propiedad como la Llave Primaria (Primary Key).
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que es autoincremental (IDENTITY(1,1)).
    @Column(name = "nro_cliente")
    private Integer nroCliente;

    @Column(name = "apellido", length = 120, nullable = false)
    private String apellido;

    @Column(name = "nombre", length = 120, nullable = false)
    private String nombre;

    @Column(name = "clave", length = 255, nullable = false)
    private String clave; // En un futuro, aquí se guardará el hash (clave encriptada).

    @Column(name = "correo", length = 180, nullable = false, unique = true)
    private String correo;

    @Column(name = "telefonos", length = 200)
    private String telefonos;

    // Asumimos que nro_localidad es solo un ID por ahora, no un objeto complejo.
    @Column(name = "nro_localidad")
    private Integer nroLocalidad;

    @Column(name = "habilitado", nullable = false)
    private boolean habilitado;



    public Integer getNroCliente() {
        return nroCliente;
    }

    public void setNroCliente(Integer nroCliente) {
        this.nroCliente = nroCliente;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefonos() {
        return telefonos;
    }

    public void setTelefonos(String telefonos) {
        this.telefonos = telefonos;
    }

    public Integer getNroLocalidad() {
        return nroLocalidad;
    }

    public void setNroLocalidad(Integer nroLocalidad) {
        this.nroLocalidad = nroLocalidad;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    // 💡 Consejo: Usa (Alt + Insert) en IntelliJ para generar
    // constructores vacíos, constructores con campos, y los Getters y Setters.
    // JPA los necesita para funcionar.
}