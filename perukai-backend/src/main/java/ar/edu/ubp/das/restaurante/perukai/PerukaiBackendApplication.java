package ar.edu.ubp.das.restaurante.perukai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class PerukaiBackendApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PerukaiBackendApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PerukaiBackendApplication.class, args);
    }

}
