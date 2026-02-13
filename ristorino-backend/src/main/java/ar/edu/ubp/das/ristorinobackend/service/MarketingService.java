package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.models.RestauranteBean;
import ar.edu.ubp.das.ristorinobackend.repositories.ClickRepository;
import ar.edu.ubp.das.ristorinobackend.repositories.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
public class MarketingService {

    @Autowired
    private RestauranteRepository restauranteRepo;

    @Autowired
    private ClickRepository clickRepo;

    // 1. Obtener Banners (Consume el servicio externo)
    public List<Map<String, Object>> obtenerContenido(int idRestaurante) {
        RestauranteBean resto = restauranteRepo.buscarPorId(idRestaurante);

        if ("REST".equals(resto.getTipoConexion())) {
            RestTemplate restTemplate = new RestTemplate();
            // Hash simulado para pasar la seguridad del restaurante
            String hash = Base64.getEncoder().encodeToString(String.valueOf(resto.getId()).getBytes());
            String url = resto.getEndpointUrl() + "/marketing?hash=" + hash;

            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            return (List<Map<String, Object>>) response.get("contenido_marketing");
        }

        // Si fuera SOAP, habría que implementar la llamada SOAP aquí...
        return List.of();
    }

    // 2. Registrar Clic (Con validación F5)
    @Transactional // IMPORTANTE: Manejo transaccional que pidió la profesora
    public String registrarInteraccion(int idRestaurante, int idCliente, String tituloContenido) {

        // A. Validar Fraude (F5)
        if (clickRepo.yaHizoClickHoy(idRestaurante, idCliente, tituloContenido)) {
            System.out.println("FRAUDE DETECTADO: El usuario " + idCliente + " intentó monetizar doble.");
            return "Click ya registrado hoy (No se cobra)";
        }

        // B. Registrar Clic (Si pasó la validación)
        clickRepo.registrarClick(idRestaurante, idCliente, tituloContenido);
        return "Click monetizado exitosamente";
    }
}