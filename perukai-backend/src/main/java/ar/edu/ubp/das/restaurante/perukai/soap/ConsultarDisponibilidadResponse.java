package ar.edu.ubp.das.restaurante.perukai.soap;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"mensaje", "sugerenciaChef", "turnosDisponibles"})
@XmlRootElement(namespace = "http://das.ubp.edu.ar/soap", name = "consultarDisponibilidadResponse")
public class ConsultarDisponibilidadResponse {

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap", required = true)
    protected String mensaje;

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap", required = true)
    protected String sugerenciaChef;

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap")
    protected List<String> turnosDisponibles;

    public String getMensaje() { return mensaje; }
    public void setMensaje(String value) { this.mensaje = value; }

    public String getSugerenciaChef() { return sugerenciaChef; }
    public void setSugerenciaChef(String value) { this.sugerenciaChef = value; }

    public List<String> getTurnosDisponibles() {
        if (turnosDisponibles == null) turnosDisponibles = new ArrayList<>();
        return this.turnosDisponibles;
    }
}