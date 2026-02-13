package ar.edu.ubp.das.restaurante.perukai.endpoint;

import ar.edu.ubp.das.restaurante.perukai.soap.ConsultarDisponibilidadRequest;
import ar.edu.ubp.das.restaurante.perukai.soap.ConsultarDisponibilidadResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class PerukaiEndpoint {

    private static final String NAMESPACE_URI = "http://das.ubp.edu.ar/soap";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "consultarDisponibilidadRequest")
    @ResponsePayload
    public ConsultarDisponibilidadResponse procesarConsulta(@RequestPayload ConsultarDisponibilidadRequest request) {

        ConsultarDisponibilidadResponse response = new ConsultarDisponibilidadResponse();

        if ("Nikkei".equalsIgnoreCase(request.getTipoCocina())) {
            response.setMensaje("Bienvenido a Perukai - Especialidad Japonesa/Peruana");
            response.setSugerenciaChef("Ceviche Clásico");
            response.getTurnosDisponibles().add("20:30");
            response.getTurnosDisponibles().add("22:00");
        } else {
            response.setMensaje("Solo servimos cocina Nikkei");
            response.setSugerenciaChef("N/A");
        }

        return response;
    }
}