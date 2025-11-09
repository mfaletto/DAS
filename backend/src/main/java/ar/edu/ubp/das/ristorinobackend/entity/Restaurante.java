package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurantes", schema = "dbo")
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "nro_restaurante")
    private Integer nroRestaurante;

    @Column(name = "razon_social", length = 200, nullable = false)
    private String razonSocial;

    @Column(name = "cuit", length = 20, nullable = false, unique = true)
    private String cuit;

    public Integer getNroRestaurante() {
        return nroRestaurante;
    }

    public void setNroRestaurante(Integer nroRestaurante) {
        this.nroRestaurante = nroRestaurante;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    // 💡 Consejo: Genera Getters y Setters
    // ...
}