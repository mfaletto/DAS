package ar.edu.ubp.das.ristorinobackend.service;

import ar.edu.ubp.das.ristorinobackend.entity.ClicksContenidosRestaurantes;
import ar.edu.ubp.das.ristorinobackend.repository.ClicksContenidosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
    
    /**
     * Proceso programado que se ejecuta cada 30 segundos.
     * 
     * Este método:
     * 1. Busca todos los clics que aún no han sido notificados (notificado = false)
     * 2. Para cada clic, intenta notificar al restaurante correspondiente
     * 3. Si la notificación es exitosa, marca el clic como notificado
     * 
     * La anotación @Scheduled permite ejecutar este método automáticamente.
     * fixedDelay = 30000 significa que se ejecuta cada 30 segundos después de que termine la ejecución anterior.
     */
    @Scheduled(fixedDelay = 30000) // Ejecuta cada 30 segundos
    public void procesarClicsPendientes() {
        logger.info("Iniciando proceso de notificación de clics pendientes...");
        
        // 1. Buscar todos los clics no notificados
        List<ClicksContenidosRestaurantes> clicsPendientes = clicksRepository.findClicksNoNotificados();
        
        if (clicsPendientes.isEmpty()) {
            logger.debug("No hay clics pendientes de notificación.");
            return;
        }
        
        logger.info("Encontrados {} clic(s) pendientes de notificación.", clicsPendientes.size());
        
        // 2. Procesar cada clic pendiente
        int notificadosExitosos = 0;
        int notificadosFallidos = 0;
        
        for (ClicksContenidosRestaurantes click : clicsPendientes) {
            try {
                // Intentar notificar al restaurante
                boolean notificacionExitosa = notificacionService.notificarRestaurante(click);
                
                if (notificacionExitosa) {
                    // Marcar como notificado
                    click.setNotificado(true);
                    clicksRepository.save(click);
                    notificadosExitosos++;
                    logger.info("Clic {} marcado como notificado exitosamente.", click.getNroClick());
                } else {
                    notificadosFallidos++;
                    logger.warn("No se pudo notificar el clic {}. Se reintentará en la próxima ejecución.", 
                            click.getNroClick());
                }
                
            } catch (Exception e) {
                notificadosFallidos++;
                logger.error("Error al procesar clic {}: {}", click.getNroClick(), e.getMessage(), e);
            }
        }
        
        logger.info("Proceso de notificación completado. Exitosos: {}, Fallidos: {}", 
                notificadosExitosos, notificadosFallidos);
    }
    
    /**
     * Método para procesar clics pendientes manualmente (útil para pruebas).
     * Puede ser llamado desde un controlador para ejecutar el proceso de forma inmediata.
     */
    public void procesarClicsPendientesManual() {
        logger.info("Ejecutando proceso de notificación manual...");
        procesarClicsPendientes();
    }
}

