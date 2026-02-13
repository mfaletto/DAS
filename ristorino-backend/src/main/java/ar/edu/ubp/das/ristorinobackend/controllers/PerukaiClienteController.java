package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.models.soap.ConsultarDisponibilidadResponse;
import ar.edu.ubp.das.ristorinobackend.utils.SOAPClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/restaurantes/perukai")
public class PerukaiClienteController {

    @GetMapping("/disponibilidad")
    public ResponseEntity<?> consultarDisponibilidad(@RequestParam String cocina) {
        try {
            // 1. Configuración Dinámica (Requisito PDF):
            // Simulamos que este JSON podría venir de una Base de Datos de configuración
            String jsonConfig = """
                {
                    "wsdlUrl": "http://localhost:8082/ws/perukai.wsdl",
                    "namespace": "http://das.ubp.edu.ar/soap",
                    "serviceName": "PerukaiPortService",
                    "portName": "PerukaiPortSoap11",
                    "username": "admin",
                    "password": "123"
                }
            """;

            // 2. Construir el Cliente usando el Builder del PDF
            SOAPClient client = SOAPClient.SOAPClientBuilder.fromConfig(jsonConfig)
                    .operationName("consultarDisponibilidadRequest") // El nombre del root element en el XSD
                    .build();

            // 3. Preparar los parámetros
            // El SOAPClient toma un Mapa y lo convierte en tags XML dentro de la operación
            Map<String, Object> params = new HashMap<>();
            params.put("tipoCocina", cocina); // Esto generará <tipoCocina>Valor</tipoCocina>

            // 4. Invocar al Servicio
            // Usamos callServiceForObject porque esperamos un solo objeto de respuesta (que contiene listas adentro)
            ConsultarDisponibilidadResponse respuesta = client.callServiceForObject(
                    ConsultarDisponibilidadResponse.class,
                    "consultarDisponibilidadResponse",
                    params
            );

            return ResponseEntity.ok(respuesta);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error SOAP: " + e.getMessage());
        }
    }
}