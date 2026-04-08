package ar.edu.ubp.das.ristorinobackend.controllers;

import ar.edu.ubp.das.ristorinobackend.models.ClienteBean;
import ar.edu.ubp.das.ristorinobackend.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200") // Permite que Angular se conecte
public class AuthController {

    @Autowired
    private ClienteRepository clienteRepository;

    // --- LOGIN (Mantenemos el que ya funcionaba) ---
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String correo = credenciales.get("correo");
        String clave = credenciales.get("clave");

        // Usamos el método que ya tenías en el Repo
        ClienteBean cliente = clienteRepository.buscarPorCorreoYClave(correo, clave);

        if (cliente != null) {
            // Login exitoso: Devolvemos el objeto cliente (sin la clave idealmente, pero para el TP sirve)
            return ResponseEntity.ok(cliente);
        } else {
            return ResponseEntity.status(401).body(Map.of("mensaje", "Credenciales inválidas"));
        }
    }

    // --- REGISTRO (NUEVO - Req 1 Obligatorio) ---
    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody ClienteBean nuevoCliente) {
        try {
            // 1. Validar campos obligatorios básicos
            if (nuevoCliente.getCorreo() == null || nuevoCliente.getClave() == null) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "Faltan datos obligatorios"));
            }

            // 2. Validar si ya existe el correo (Regla de negocio del PDF)
            if (clienteRepository.existeUsuario(nuevoCliente.getCorreo())) {
                return ResponseEntity.badRequest().body(Map.of("mensaje", "El correo electrónico ya está registrado"));
            }

            // 3. Insertar en Base de Datos
            int resultado = clienteRepository.registrar(nuevoCliente);

            if (resultado > 0) {
                return ResponseEntity.ok(Map.of("mensaje", "Usuario registrado con éxito"));
            } else {
                return ResponseEntity.internalServerError().body(Map.of("mensaje", "No se pudo registrar el usuario en la base de datos"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(Map.of("mensaje", "Error del servidor: " + e.getMessage()));
        }
    }
}