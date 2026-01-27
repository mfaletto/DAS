package ar.edu.ubp.das.ristorinobackend.bean;

public class RestauranteBean {

    private Integer nroRestaurante;
    private String razonSocial;
    private String cuit;
    private String emailContacto;
    private String urlNotificacion;

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

    public String getEmailContacto() {
        return emailContacto;
    }

    public void setEmailContacto(String emailContacto) {
        this.emailContacto = emailContacto;
    }

    public String getUrlNotificacion() {
        return urlNotificacion;
    }

    public void setUrlNotificacion(String urlNotificacion) {
        this.urlNotificacion = urlNotificacion;
    }
}
