package ar.edu.ubp.das.ristorinobackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/api/ia")
@CrossOrigin(origins = "http://localhost:4200")
public class IAController {

    // REQ 35: Interpretar búsqueda en Lenguaje Natural
    // Ejemplo: "Quiero comer sushi barato" -> { categoria: "SUSHI", precio: "BAJO" }
    @PostMapping("/interpretar")
    public ResponseEntity<?> interpretarBusqueda(@RequestBody Map<String, String> body) {
        String textoRaw = body.get("texto");
        if (textoRaw == null || textoRaw.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El campo 'texto' es obligatorio"));
        }
        String texto = textoRaw.toLowerCase();

        // Simulación de NLP (Procesamiento de Lenguaje Natural) básica
        String categoriaDetectada = "VARIOS";
        if (texto.contains("pizza") || texto.contains("italiana")) categoriaDetectada = "PIZZA";
        else if (texto.contains("sushi") || texto.contains("asiatica")) categoriaDetectada = "SUSHI";
        else if (texto.contains("hamburguesa") || texto.contains("carne")) categoriaDetectada = "HAMBURGUESAS";
        else if (texto.contains("vegano") || texto.contains("ensalada")) categoriaDetectada = "VEGANO";

        return ResponseEntity.ok(Map.of(
                "intencion", "buscar_restaurante",
                "entidades", Map.of(
                        "categoria", categoriaDetectada,
                        "confianza", "0.98" // Fingimos un score de IA
                ),
                "respuesta_ia", "¡Entendido! Busqué las mejores opciones de " + categoriaDetectada + " para vos. 🤖"
        ));
    }

    // REQ 34: Generar contenido promocional (Copywriting)
    @GetMapping("/generar-copy")
    public ResponseEntity<?> generarCopy(@RequestParam String restaurante, @RequestParam String tipoComida) {
        String[] frasesInicio = {
                "¡Descubrí la magia de " + restaurante + "! ✨",
                "¿Hambre de algo rico? Vení a " + restaurante + ". 🍽️",
                "La mejor experiencia en " + tipoComida + " te espera.",
                "¡Alerta Foodie! 🚨 No te pierdas los sabores de " + restaurante + "."
        };

        String[] frasesCierre = {
                " Reservá tu mesa ahora.",
                " Una experiencia inolvidable.",
                " Calidad garantizada por Ristorino AI.",
                " Ideal para compartir con amigos."
        };

        // Elegimos frases al azar para que parezca que la IA "crea" texto nuevo
        Random random = new Random();
        String copyGenerado = frasesInicio[random.nextInt(frasesInicio.length)] +
                " Disfrutá de platos únicos con ingredientes frescos." +
                frasesCierre[random.nextInt(frasesCierre.length)];

        return ResponseEntity.ok(Map.of("contenido", copyGenerado));
    }
}