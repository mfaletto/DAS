package ar.edu.ubp.das.batch;

import ar.edu.ubp.das.batch.dto.ClickDTO;
import ar.edu.ubp.das.batch.dto.ProcesoNotificacionDTO;
import ar.edu.ubp.das.batch.service.NotificacionClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BatchNotificadorApplication {

    private static final Logger logger = LoggerFactory.getLogger(BatchNotificadorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BatchNotificadorApplication.class, args).close();
    }

    @Bean
    CommandLineRunner ejecutar(NotificacionClient client) {
        return args -> {
            logger.info("=== Batch de notificaciones Ristorino ===");

            List<ClickDTO> pendientes = client.obtenerPendientes();
            if (pendientes.isEmpty()) {
                logger.info("No se encontraron clics pendientes. Nada para procesar.");
                return;
            }

            logger.info("Se encontraron {} clic(s) pendientes.", pendientes.size());
            pendientes.forEach(click -> logger.info("- Clic {} | Restaurante {} | Contenido {}",
                    click.getNroClick(), click.getNroRestaurante(), click.getNroContenido()));

            ProcesoNotificacionDTO resultado = client.procesarPendientes();
            logger.info("Proceso finalizado: total={}, exitosos={}, fallidos={}",
                    resultado.getTotal(), resultado.getExitosos(), resultado.getFallidos());

            if (resultado.getFallidos() > 0) {
                logger.warn("Quedaron {} clic(s) sin notificar. Reintentar en la próxima ejecución.",
                        resultado.getFallidos());
            }
        };
    }
}
