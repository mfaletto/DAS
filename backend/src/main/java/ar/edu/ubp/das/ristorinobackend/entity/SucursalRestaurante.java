package ar.edu.ubp.das.ristorinobackend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "sucursales_restaurantes", schema = "dbo")
public class SucursalRestaurante {

    // 1. CLAVE CORRECTA: Usa solo el objeto clave compuesto
    @EmbeddedId
    private SucursalRestauranteId id;

    // ⚠️ ATENCIÓN: Los campos nro_restaurante y nro_sucursal se gestionan
    // solo en la clase SucursalRestauranteId.

    @Column(name = "nom_sucursal", length = 150, nullable = false)
    private String nomSucursal;

    @Column(name = "calle", length = 200)
    private String calle;

    @Column(name = "nro_calle", length = 20)
    private String nroCalle;


    // (Omitimos el resto de campos complejos por ahora, como localidad, para ir al detalle)

    // 💡 Consejo: Genera Getters y Setters
    // ...

    // Getter especial para la clave
    public SucursalRestauranteId getId() {
        return id;
    }
    public void setId(SucursalRestauranteId id) {
        this.id = id;
    }


    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNroCalle() {
        return nroCalle;
    }

    public void setNroCalle(String nroCalle) {
        this.nroCalle = nroCalle;
    }


    public String getNomSucursal() {
        return nomSucursal;
    }

    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
    }
}