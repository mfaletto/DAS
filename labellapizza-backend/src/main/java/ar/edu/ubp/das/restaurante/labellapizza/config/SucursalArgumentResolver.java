package ar.edu.ubp.das.restaurante.labellapizza.config;

import ar.edu.ubp.das.restaurante.labellapizza.model.Sucursal;
import ar.edu.ubp.das.restaurante.labellapizza.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Base64;

@Component
public class SucursalArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private SucursalRepository repository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // Solo actúa si el parámetro tiene nuestra anotación @SucursalParam
        return parameter.hasParameterAnnotation(SucursalParam.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 1. Buscamos el parámetro "hash" en la URL (ej: ?hash=TVRVPQ==)
        String hash = webRequest.getParameter("hash");

        if (hash == null) {
            throw new IllegalArgumentException("El parámetro hash es obligatorio");
        }

        try {
            // 2. Desencriptamos el Hash (Simulación académica con Base64)
            byte[] decodedBytes = Base64.getDecoder().decode(hash);
            String idString = new String(decodedBytes); // Debería dar algo como "1"
            int id = Integer.parseInt(idString);

            // 3. Buscamos en el repo (usando el método que creamos ayer)
            return repository.buscarPorId(id)
                    .orElseThrow(() -> new RuntimeException("Sucursal no encontrada con hash: " + hash));

        } catch (Exception e) {
            // Si el hash es inválido o no existe, lanzamos error
            // El GlobalExceptionHandler capturará esto y devolverá un JSON lindo
            throw new RuntimeException("Hash de sucursal inválido");
        }
    }
}