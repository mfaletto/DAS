package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.entity.ClicksContenidosRestaurantes;
import ar.edu.ubp.das.ristorinobackend.entity.Restaurante;
import ar.edu.ubp.das.ristorinobackend.repository.RestauranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Servicio que se encarga de notificar a los restaurantes sobre los clics recibidos.
 * 
 * Este servicio implementa la integración con servicios REST externos de restaurantes.
 * Para el entregable, se simula un servicio REST de ejemplo.
 */
@Service
public class RestauranteNotificacionService {

    private static final Logger logger = LoggerFactory.getLogger(RestauranteNotificacionService.class);
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    // RestTemplate para hacer llamadas HTTP a servicios REST externos
    private final RestTemplate restTemplate = new RestTemplate();
    
    /**
     * Notifica a un restaurante sobre un clic recibido en su promoción.
     * 
     * En un escenario real, este método llamaría al servicio REST o SOAP del restaurante.
     * Para este entregable, simulamos la notificación a un servicio REST de ejemplo.
     * 
     * @param click El registro del clic que se debe notificar
     * @return true si la notificación fue exitosa, false en caso contrario
     */
    public boolean notificarRestaurante(ClicksContenidosRestaurantes click) {
        try {
            // 1. Obtener información del restaurante
            Integer nroRestaurante = click.getContenido().getId().getNroRestaurante();
            Restaurante restaurante = restauranteRepository.findById(nroRestaurante)
                    .orElseThrow(() -> new RuntimeException("Restaurante no encontrado: " + nroRestaurante));
            
            // 2. Preparar los datos de la notificación
            Map<String, Object> notificacionData = new HashMap<>();
            notificacionData.put("nroRestaurante", nroRestaurante);
            notificacionData.put("razonSocial", restaurante.getRazonSocial());
            notificacionData.put("nroClick", click.getNroClick());
            notificacionData.put("fechaHoraRegistro", click.getFechaHoraRegistro().toString());
            notificacionData.put("costoClick", click.getCostoClick());
            notificacionData.put("contenidoPromocional", click.getContenido().getContenidoPromocional());
            
            // 3. URL del servicio REST del restaurante (simulado)
            // En producción, esta URL vendría de la configuración o de la base de datos
            String restauranteUrl = obtenerUrlRestaurante(nroRestaurante);
            
            // 4. Configurar headers HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(notificacionData, headers);
            
            // 5. Enviar la notificación al servicio REST del restaurante
            logger.info("Enviando notificación de clic {} al restaurante {} en {}", 
                    click.getNroClick(), restaurante.getRazonSocial(), restauranteUrl);
            
            ResponseEntity<String> response = restTemplate.postForEntity(
                    restauranteUrl + "/api/notificaciones/click",
                    request,
                    String.class
            );
            
            // 6. Verificar que la respuesta sea exitosa
            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Notificación exitosa para clic {} - Restaurante: {}", 
                        click.getNroClick(), restaurante.getRazonSocial());
                return true;
            } else {
                logger.warn("Notificación fallida para clic {} - Código: {}", 
                        click.getNroClick(), response.getStatusCode());
                return false;
            }
            
        } catch (Exception e) {
            logger.error("Error al notificar restaurante para clic {}: {}", 
                    click.getNroClick(), e.getMessage(), e);
            return false;
        }
    }
    
    /**
     * Obtiene la URL del servicio REST del restaurante.
     * 
     * Para este entregable, simulamos URLs de ejemplo.
     * En producción, estas URLs deberían estar almacenadas en la base de datos.
     * 
     * NOTA: Para este entregable, el servicio mock está en el mismo servidor.
     * En producción, cada restaurante tendría su propio servidor.
     * 
     * @param nroRestaurante Número del restaurante
     * @return URL del servicio REST del restaurante
     */
    private String obtenerUrlRestaurante(Integer nroRestaurante) {
        // Simulación: diferentes restaurantes podrían tener diferentes URLs
        // En producción, esto vendría de la base de datos
        
        // Para este entregable, el servicio mock está en el mismo servidor (puerto 8080)
        // En producción, cada restaurante tendría su propio servidor (ej: puerto 8081, 8082, etc.)
        return "http://localhost:8080/api/v1"; // Servicio REST simulado en el mismo backend
    }
}

