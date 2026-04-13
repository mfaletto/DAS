package ar.edu.ubp.das.ristorinobackend.service;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

/**
 * Cliente REST alternativo usando Jersey (JAX-RS).
 * Coexiste con el enfoque existente basado en RestTemplate en PortalService.
 */
@Service
public class JerseyRestClientService {

    private final Client client;

    public JerseyRestClientService() {
        this.client = ClientBuilder.newClient();
    }

    // Consumir disponibilidad de La Bella Pizza via Jersey JAX-RS client
    public Map<String, Object> consultarDisponibilidadLaBellaPizza(String baseUrl, int sucursalId) {
        String hash = Base64.getEncoder().encodeToString(String.valueOf(sucursalId).getBytes());

        WebTarget target = client.target(baseUrl)
                .path("/disponibilidad")
                .queryParam("hash", hash);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            return response.readEntity(Map.class);
        } else {
            throw new RuntimeException("Error desde La Bella Pizza: HTTP " + response.getStatus());
        }
    }

    // Consumir marketing de La Bella Pizza via Jersey JAX-RS client
    public Map<String, Object> obtenerMarketingLaBellaPizza(String baseUrl, int sucursalId) {
        String hash = Base64.getEncoder().encodeToString(String.valueOf(sucursalId).getBytes());

        WebTarget target = client.target(baseUrl)
                .path("/marketing")
                .queryParam("hash", hash);

        Response response = target.request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() == 200) {
            return response.readEntity(Map.class);
        } else {
            throw new RuntimeException("Error desde La Bella Pizza marketing: HTTP " + response.getStatus());
        }
    }
}
