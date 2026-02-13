package ar.edu.ubp.das.restaurante.perukai.soap;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"tipoCocina"})
@XmlRootElement(namespace = "http://das.ubp.edu.ar/soap", name = "consultarDisponibilidadRequest")
public class ConsultarDisponibilidadRequest {

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap", required = true)
    protected String tipoCocina;

    public String getTipoCocina() { return tipoCocina; }
    public void setTipoCocina(String value) { this.tipoCocina = value; }
}