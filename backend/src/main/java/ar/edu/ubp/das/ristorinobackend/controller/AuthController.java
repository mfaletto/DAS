package ar.edu.ubp.das.ristorinobackend.controller;

import ar.edu.ubp.das.ristorinobackend.dto.LoginRequest;
import ar.edu.ubp.das.ristorinobackend.dto.LoginResponse;
import ar.edu.ubp.das.ristorinobackend.dto.RegistroRequest;
import ar.edu.ubp.das.ristorinobackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // URL base: /api/v1/auth
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * ENDPOINT: POST /api/v1/auth/register
     * Requisito: Dar de alta usuario (RF 1)
     */
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestBody RegistroRequest request) {
        try {
            authService.registrar(request);
            return ResponseEntity.status(201).build(); // 201 Created
        } catch (Exception e) {
            // Error común: el correo ya existe (ConstraintViolationException)
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(401).build();
        }
    }
}