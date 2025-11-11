package com.ristorino.controller;

import com.ristorino.dto.RegistroRequest;
import com.ristorino.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService service) {
        this.authService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest req) {
        authService.registrarClienteBasico(req);
        return ResponseEntity.ok().build();
    }
}
