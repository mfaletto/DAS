package ar.edu.ubp.das.batch.service;

import ar.edu.ubp.das.batch.dto.ClickDTO;
import ar.edu.ubp.das.batch.dto.ProcesoNotificacionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class NotificacionClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public NotificacionClient(RestTemplateBuilder builder,
                               @Value("${backend.base-url:http://localhost:8080/api/v1}") String baseUrl) {
        this.restTemplate = builder.build();
        this.baseUrl = baseUrl;
    }

    public List<ClickDTO> obtenerPendientes() {
        ResponseEntity<ClickDTO[]> response = restTemplate.getForEntity(
                baseUrl + "/notificaciones/pendientes",
                ClickDTO[].class
        );
        ClickDTO[] body = response.getBody();
        if (body == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(body);
    }

    public ProcesoNotificacionDTO procesarPendientes() {
        return restTemplate.postForObject(
                baseUrl + "/notificaciones/procesar",
                null,
                ProcesoNotificacionDTO.class
        );
    }

    public void marcarNotificado(Integer nroClick) {
        restTemplate.postForEntity(baseUrl + "/notificaciones/marcar/" + nroClick,
                null,
                Void.class);
    }
}
