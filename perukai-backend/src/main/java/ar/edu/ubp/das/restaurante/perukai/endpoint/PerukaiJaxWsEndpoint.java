package ar.edu.ubp.das.restaurante.perukai.endpoint;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@WebService(serviceName = "PerukaiJaxWsService", targetNamespace = "http://das.ubp.edu.ar/soap")
public class PerukaiJaxWsEndpoint {

    @WebMethod(operationName = "consultarMenu")
    public String consultarMenu(@WebParam(name = "tipoCocina") String tipoCocina) {
        if ("Nikkei".equalsIgnoreCase(tipoCocina)) {
            return "Menu Nikkei: Ceviche Clasico, Tiradito de Salmon, Lomo Saltado, Maki Acevichado";
        }
        return "Solo servimos cocina Nikkei. Prueba con tipoCocina=Nikkei";
    }
}
