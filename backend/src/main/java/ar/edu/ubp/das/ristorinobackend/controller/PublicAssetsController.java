package ar.edu.ubp.das.ristorinobackend.controller;

import ar.edu.ubp.das.ristorinobackend.service.PromocionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/public/assets")
@CrossOrigin(origins = "http://localhost:4200")
public class PublicAssetsController {

    @Autowired
    private PromocionService promocionService;

    @GetMapping("/{hash}")
    public ResponseEntity<Void> obtenerRecurso(@PathVariable String hash) {
        return promocionService.obtenerUrlImagenPorHash(hash)
                .map(url -> ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create(url))
                        .<Void>build())
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
