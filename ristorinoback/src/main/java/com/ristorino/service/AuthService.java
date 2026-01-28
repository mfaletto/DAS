package com.ristorino.service;

import com.ristorino.dto.RegistroRequest;
import com.ristorino.repository.AuthRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthRepository repo;

    public AuthService(AuthRepository repo) {
        this.repo = repo;
    }

    public void registrarClienteBasico(RegistroRequest req) {
        repo.insertarClienteBasico(req);
    }
}
