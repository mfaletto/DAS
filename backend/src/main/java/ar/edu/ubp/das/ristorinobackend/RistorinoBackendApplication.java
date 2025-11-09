package ar.edu.ubp.das.ristorinobackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación Spring Boot.
 * 
 * @EnableScheduling habilita la ejecución de tareas programadas (@Scheduled),
 * necesario para el proceso automático de notificación de clics a restaurantes.
 */
@SpringBootApplication
@EnableScheduling
public class RistorinoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(RistorinoBackendApplication.class, args);
    }

}
