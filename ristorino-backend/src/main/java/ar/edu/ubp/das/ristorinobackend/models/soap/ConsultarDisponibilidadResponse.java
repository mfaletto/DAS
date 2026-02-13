package ar.edu.ubp.das.ristorinobackend.models.soap;

import jakarta.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "consultarDisponibilidadResponse", namespace = "http://das.ubp.edu.ar/soap")
public class ConsultarDisponibilidadResponse {

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap")
    protected String mensaje;

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap")
    protected String sugerenciaChef;

    @XmlElement(namespace = "http://das.ubp.edu.ar/soap")
    protected List<String> turnosDisponibles;

    // Getters y Setters
    public String getMensaje() { return mensaje; }
    public void setMensaje(String value) { this.mensaje = value; }

    public String getSugerenciaChef() { return sugerenciaChef; }
    public void setSugerenciaChef(String value) { this.sugerenciaChef = value; }

    public List<String> getTurnosDisponibles() {
        if (turnosDisponibles == null) {
            turnosDisponibles = new ArrayList<>();
        }
        return this.turnosDisponibles;
    }
}