package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.models.RestauranteBean;
import ar.edu.ubp.das.ristorinobackend.models.soap.ConsultarDisponibilidadResponse;
import ar.edu.ubp.das.ristorinobackend.repositories.RestauranteRepository;
import ar.edu.ubp.das.ristorinobackend.utils.SOAPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class PortalService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    public Object consultarDisponibilidad(int restauranteId, Map<String, String> criterios) {
        // 1. Buscamos la info en la DB
        RestauranteBean resto = restauranteRepository.buscarPorId(restauranteId);
        if (resto == null) {
            throw new RuntimeException("Restaurante no encontrado");
        }

        // 2. Despachamos según el tipo
        if ("REST".equalsIgnoreCase(resto.getTipoConexion())) {
            return llamarARest(resto, criterios);
        } else if ("SOAP".equalsIgnoreCase(resto.getTipoConexion())) {
            return llamarASoap(resto, criterios);
        } else {
            throw new RuntimeException("Tipo de conexión no soportado: " + resto.getTipoConexion());
        }
    }

    // --- LÓGICA REST (La Bella Pizza) ---
    private Object llamarARest(RestauranteBean resto, Map<String, String> criterios) {
        RestTemplate restTemplate = new RestTemplate();

        // Asumimos que La Bella Pizza requiere un ID hasheado (simulado) y fecha
        // endpointUrl en DB: http://localhost:8081/api/v1/public

        String fecha = criterios.getOrDefault("fecha", "2023-10-10");
        String hash = Base64.getEncoder().encodeToString("1".getBytes()); // Simulamos el hash MQ==

        String urlFinal = resto.getEndpointUrl() + "/disponibilidad?fecha=" + fecha + "&hash=" + hash;

        // Retornamos el Map que devuelve el REST
        return restTemplate.getForObject(urlFinal, Map.class);
    }

    // --- LÓGICA SOAP (Perukai) ---
    private Object llamarASoap(RestauranteBean resto, Map<String, String> criterios) {
        try {
            // Construimos el JSON de configuración dinámicamente con la URL de la DB
            String jsonConfig = String.format("""
                {
                    "wsdlUrl": "%sperukai.wsdl",
                    "namespace": "http://das.ubp.edu.ar/soap",
                    "serviceName": "PerukaiPortService",
                    "portName": "PerukaiPortSoap11",
                    "username": "admin",
                    "password": "123"
                }
            """, resto.getEndpointUrl()); // endpointUrl en DB: http://localhost:8082/ws/

            SOAPClient client = SOAPClient.SOAPClientBuilder.fromConfig(jsonConfig)
                    .operationName("consultarDisponibilidadRequest")
                    .build();

            Map<String, Object> params = new HashMap<>();
            params.put("tipoCocina", criterios.getOrDefault("tipoCocina", "Nikkei"));

            return client.callServiceForObject(
                    ConsultarDisponibilidadResponse.class,
                    "consultarDisponibilidadResponse",
                    params
            );

        } catch (Exception e) {
            throw new RuntimeException("Error en SOAP: " + e.getMessage());
        }
    }
}