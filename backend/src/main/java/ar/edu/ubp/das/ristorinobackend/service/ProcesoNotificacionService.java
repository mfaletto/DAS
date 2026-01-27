package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.bean.ClickBean;
import ar.edu.ubp.das.ristorinobackend.repository.ClicksContenidosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio que procesa los clics pendientes de notificación.
 * 
 * Este es el "proceso independiente del backend" mencionado en la consigna.
 * Se ejecuta periódicamente para recorrer los registros pendientes y notificar a los restaurantes.
 */
@Service
public class ProcesoNotificacionService {

    private static final Logger logger = LoggerFactory.getLogger(ProcesoNotificacionService.class);
    
    @Autowired
    private ClicksContenidosRepository clicksRepository;
    
    @Autowired
    private RestauranteNotificacionService notificacionService;
    
    public ProcesoNotificacionResult procesarClicsPendientes() {
        logger.info("Iniciando proceso de notificación de clics pendientes...");

        List<ClickBean> clicsPendientes = clicksRepository.obtenerClicksPendientes();

        if (clicsPendientes.isEmpty()) {
            logger.debug("No hay clics pendientes de notificación.");
            return ProcesoNotificacionResult.sinPendientes();
        }

        logger.info("Encontrados {} clic(s) pendientes de notificación.", clicsPendientes.size());

        int notificadosExitosos = 0;
        int notificadosFallidos = 0;

        for (ClickBean click : clicsPendientes) {
            try {
                boolean notificacionExitosa = notificacionService.notificarRestaurante(click);

                if (notificacionExitosa) {
                    click.setNotificado(true);
                    clicksRepository.actualizarEstadoNotificado(click);
                    notificadosExitosos++;
                    logger.info("Clic {} marcado como notificado exitosamente.", click.getNroClick());
                } else {
                    notificadosFallidos++;
                    logger.warn("No se pudo notificar el clic {}. Queda pendiente para próximo ciclo.",
                            click.getNroClick());
                }

            } catch (Exception e) {
                notificadosFallidos++;
                logger.error("Error al procesar clic {}: {}", click.getNroClick(), e.getMessage(), e);
            }
        }

        return new ProcesoNotificacionResult(clicsPendientes.size(), notificadosExitosos, notificadosFallidos);
    }

    public static class ProcesoNotificacionResult {
        private final int total;
        private final int exitosos;
        private final int fallidos;

        public ProcesoNotificacionResult(int total, int exitosos, int fallidos) {
            this.total = total;
            this.exitosos = exitosos;
            this.fallidos = fallidos;
        }

        public static ProcesoNotificacionResult sinPendientes() {
            return new ProcesoNotificacionResult(0, 0, 0);
        }

        public int getTotal() {
            return total;
        }

        public int getExitosos() {
            return exitosos;
        }

        public int getFallidos() {
            return fallidos;
        }
    }
}


