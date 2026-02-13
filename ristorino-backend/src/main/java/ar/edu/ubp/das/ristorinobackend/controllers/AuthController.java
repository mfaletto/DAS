package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.models.ClienteBean;
import ar.edu.ubp.das.ristorinobackend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String clave = credenciales.get("clave");

        // Validamos que vengan los datos
        if (correo == null || clave == null) {
            return ResponseEntity.badRequest().body("Falta correo o clave");
        }

        // Usamos el repositorio JDBC para buscar en la base real
        ClienteBean cliente = clienteRepository.buscarPorCorreoYClave(correo, clave);

        if (cliente != null) {
            // Login Exitoso: Devolvemos los datos del usuario (sin la clave por seguridad)
            cliente.setClave(null);
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}