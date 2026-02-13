package ar.edu.ubp.das.restaurante.labellapizza.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class RistorinoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("[INTERCEPTOR] Petición entrante: " + request.getMethod() + " " + request.getRequestURI());

        // Aquí podrías validar headers obligatorios, tokens, etc.
        // Si retornas 'false', la petición se corta acá.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        // Se ejecuta después de que el controlador hizo su trabajo pero antes de responder al cliente
        System.out.println("[INTERCEPTOR] Petición procesada con estado: " + response.getStatus());
    }

    // afterCompletion es donde puedes capturar errores que ocurrieron en la vista o filtros finales
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex != null) {
            System.err.println("[INTERCEPTOR ERROR] Ocurrió una excepción: " + ex.getMessage());
        }
    }
}