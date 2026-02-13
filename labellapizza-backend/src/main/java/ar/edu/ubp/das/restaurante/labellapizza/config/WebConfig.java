package ar.edu.ubp.das.restaurante.labellapizza.config;

import ar.edu.ubp.das.restaurante.labellapizza.interceptor.RistorinoInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RistorinoInterceptor interceptor;

    @Autowired
    private SucursalArgumentResolver sucursalResolver; // Inyectalo

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/api/**"); // Solo intercepta nuestras APIs
    }

    // AGREGAR ESTE MÉTODO:
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(sucursalResolver);
    }
}