package ar.edu.ubp.das.ristorinobackend.bean;

public class SucursalRestauranteBean {

    private SucursalRestauranteIdBean id;
    private String nomSucursal;
    private String calle;
    private String nroCalle;
    private String localidad;

    public SucursalRestauranteIdBean getId() {
        return id;
    }

    public void setId(SucursalRestauranteIdBean id) {
        this.id = id;
    }

    public String getNomSucursal() {
        return nomSucursal;
    }

    public void setNomSucursal(String nomSucursal) {
        this.nomSucursal = nomSucursal;
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

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }
}
